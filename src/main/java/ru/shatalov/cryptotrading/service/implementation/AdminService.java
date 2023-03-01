package ru.shatalov.cryptotrading.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shatalov.cryptotrading.entity.Administrator;
import ru.shatalov.cryptotrading.entity.Currency;
import ru.shatalov.cryptotrading.entity.CurrencyAmount;
import ru.shatalov.cryptotrading.entity.Rate;
import ru.shatalov.cryptotrading.exception.NoSuchCurrencyException;
import ru.shatalov.cryptotrading.repository.*;
import ru.shatalov.cryptotrading.service.IAdminService;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminService implements IAdminService {
    private final IRateRepository rateRepository;
    private final ICurrencyAmountRepository currencyAmountRepository;
    private final ICurrencyRepository currencyRepository;
    private final ITransactionRepository transactionRepository;
    private final IAdministratorRepository administratorRepository;

    public AdminService(IRateRepository rateRepository,
                        ICurrencyAmountRepository currencyAmountRepository,
                        ICurrencyRepository currencyRepository,
                        ITransactionRepository transactionRepository,
                        IAdministratorRepository administratorRepository) {
        this.rateRepository = rateRepository;
        this.currencyAmountRepository = currencyAmountRepository;
        this.currencyRepository = currencyRepository;
        this.transactionRepository = transactionRepository;
        this.administratorRepository = administratorRepository;
    }

    @Override
    public Map<String, String> changeRateForBaseCurrencyFromStringMap(Map<String, String> map) {
        log.info("Администратор запросил изменить курс относительно валюты {}",
                map.get("base_currency"));
        Currency baseCurrency = findCurrencyByName(map.remove("base_currency"));
        for (String currencyName : map.keySet()) {
            Currency currency = findCurrencyByName(currencyName);
            Double value = Double.parseDouble(map.get(currencyName));
            Optional<Rate> rate = rateRepository
                    .findByCurrencyFromAndCurrencyTo(baseCurrency, currency);
            if (rate.isPresent()) {
                rate.get().setValue(value);
                rateRepository.save(rate.get());
            } else {
                Rate straightRate = new Rate();
                Rate reverseRate = new Rate();
                straightRate.setCurrencyFrom(baseCurrency);
                straightRate.setCurrencyTo(currency);
                straightRate.setValue(value);
                rateRepository.save(straightRate);
                reverseRate.setCurrencyFrom(currency);
                reverseRate.setCurrencyTo(baseCurrency);
                reverseRate.setValue(1 / value);
                rateRepository.save(reverseRate);
            }
        }
        Set<Rate> ratesForBaseCurrency = rateRepository.findByCurrencyFrom(baseCurrency);
        log.info("Администратор успешно изменил курс относительно валюты {}",
                map.get("base_currency"));
        return ratesForBaseCurrency.stream()
                .collect(Collectors.toMap(
                        rate -> rate.getCurrencyTo().getName(),
                        rate -> rate.getValue().toString()
                ));
    }

    @Override
    public Double getTotalAmountOfCurrency(String currencyName) {
        findCurrencyByName(currencyName);
        Double totalSum = (double) 0;
        for (CurrencyAmount c : currencyAmountRepository.findByCurrency_Name(currencyName)) {
            totalSum += c.getAmount();
        }
        log.info("Администратор запросил общую сумму на всех пользовательских счетах" +
                " для валюты {} и она составила {}", currencyName, totalSum);
        return totalSum;
    }

    @Override
    public Long getNumberOfTransactionsDependsOnDate(Date dateFrom, Date dateTo) {
        return transactionRepository.countByDateBetween(dateFrom, dateTo);
    }


    private Currency findCurrencyByName(String name) {
        Optional<Currency> currency = currencyRepository
                .findById(name);
        if (currency.isEmpty()) {
            throw new NoSuchCurrencyException();
        } else return currency.get();
    }

    @Override
    public List<UUID> getAllAdminsIds() {
        List<Administrator> adminsList = administratorRepository.findAll();
        List<UUID> adminsIdList = new ArrayList<>();
        for (Administrator a : adminsList) {
            adminsIdList.add(a.getSecretKey());
        }
        return adminsIdList;
    }

}
