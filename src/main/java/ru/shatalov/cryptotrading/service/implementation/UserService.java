package ru.shatalov.cryptotrading.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shatalov.cryptotrading.entity.Currency;
import ru.shatalov.cryptotrading.entity.*;
import ru.shatalov.cryptotrading.exception.*;
import ru.shatalov.cryptotrading.repository.*;
import ru.shatalov.cryptotrading.service.IAdminService;
import ru.shatalov.cryptotrading.service.IUserService;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IWalletRepository walletRepository;
    private final ICurrencyRepository currencyRepository;
    private final ICurrencyAmountRepository currencyAmountRepository;
    private final IRateRepository rateRepository;
    private final ITransactionRepository transactionRepository;
    private final IAdminService adminService;

    public UserService(IUserRepository userRepository,
                       IWalletRepository walletRepository,
                       ICurrencyRepository currencyRepository,
                       ICurrencyAmountRepository currencyAmountRepository,
                       IRateRepository rateRepository,
                       ITransactionRepository transactionRepository,
                       IAdminService adminService) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.currencyRepository = currencyRepository;
        this.currencyAmountRepository = currencyAmountRepository;
        this.rateRepository = rateRepository;
        this.transactionRepository = transactionRepository;
        this.adminService = adminService;
    }

    private Person getUser(String secretKey) {
        Optional<Person> person = userRepository.findById(UUID.fromString(secretKey));
        if (person.isPresent()) {
            return person.get();
        }
        throw new InvalidSecretKeyException();
    }

    private void addTransaction(Wallet walletFrom, Wallet walletTo, Currency currency) {
        Transaction transaction = new Transaction();
        transaction.setDate(new Date(System.currentTimeMillis()));
        transaction.setCurrency(currency);
        transaction.setWalletFrom(walletFrom);
        transaction.setWalletTo(walletTo);
        transactionRepository.save(transaction);
    }

    @Override
    public Map<String, String> getActualRates(String secretKey, String currencyFromName) {
        log.info("Пользователь {} запросил актуальные курсы валюты {}",
                secretKey, currencyFromName);
        if (!adminService.getAllAdminsIds().contains(UUID.fromString(secretKey))) {
            getUser(secretKey);
        }
        Currency currencyFrom = findCurrencyByName(currencyFromName);
        Set<Rate> ratesForCurrencyFrom = rateRepository.findByCurrencyFrom(currencyFrom);
        return ratesForCurrencyFrom.stream()
                .collect(Collectors.toMap(
                        rate -> rate.getCurrencyTo().getName(),
                        rate -> rate.getValue().toString()
                ));
    }

    @Override
    public Map<String, Double> getWalletBalance(String secretKey) {
        Person person = getUser(secretKey);
        log.info("Пользователь {} запросил баланс своего кошелька",
                secretKey);
        Set<CurrencyAmount> currencyAmounts = person.getWallet().getCurrencies();
        if (currencyAmounts.isEmpty()) {
            throw new NothingInWalletException();
        }
        Map<String, Double> walletMap = currencyAmounts.stream()
                .collect(Collectors.toMap(
                        currencyAmount -> currencyAmount.getCurrency().getName(),
                        CurrencyAmount::getAmount
                ));
        walletMap.keySet().forEach(walletName -> walletName = walletName.concat("_wallet"));
        log.info("Пользователь {} запросил баланс своего кошелька и получил {}",
                secretKey, walletMap);
        return walletMap;
    }

    private Optional<CurrencyAmount> getChosenCurrencyAmount(Person person, Currency currency) {
        Set<CurrencyAmount> currencyAmounts = person.getWallet().getCurrencies();
        return currencyAmounts.stream()
                .filter(currencyAmount ->
                        Objects.equals(currencyAmount.getCurrency(), currency)).
                findFirst();
    }

    private Currency findCurrencyByName(String currencyName) {
        Optional<Currency> currency = currencyRepository.findById(currencyName);
        if (currency.isPresent()) {
            return currency.get();
        } else throw new NoSuchCurrencyException();
    }

    @Override
    public Double withdrawMoney(String secretKey,
                                String currencyName,
                                String count) {
        log.info("Пользователь {} оставил запрос на снятие валюты {} в количестве {}",
                secretKey, currencyName, count);
        Person person = getUser(secretKey);
        Currency currency = findCurrencyByName(currencyName);
        Double amount = Double.parseDouble(count);
        Optional<CurrencyAmount> currencyAmount = getChosenCurrencyAmount(person, currency);
        if (currencyAmount.isPresent()) {
            Double currentAmount = currencyAmount.get().getAmount();
            if (amount <= currentAmount) {
                currencyAmount.get().setAmount(currentAmount - amount);
                currencyAmountRepository.save(currencyAmount.get());
                addTransaction(person.getWallet(), null, currency);
                log.info("Пользователь {} успешно снял валюту {} в количестве {}",
                        secretKey, currencyName, count);
                return currentAmount - amount;
            } else throw new NotEnoughMoneyException();
        } else throw new NotEnoughMoneyException();
    }

    private Rate getRate(Currency currencyFrom, Currency currencyTo) {
        Optional<Rate> rate = rateRepository.findByCurrencyFromAndCurrencyTo(currencyFrom, currencyTo);
        if (rate.isPresent()) {
            return rate.get();
        } else throw new NoSuchRateException();
    }

    @Override
    public Double exchangeCurrency(String secretKey,
                                   String currencyFromName,
                                   String currencyToName,
                                   String amount) {
        log.info("Пользователь {} хочет обменять валюту {}" +
                        " на валюту {}" +
                        " в количестве: {}",
                secretKey, currencyFromName, currencyToName, amount);
        Person person = getUser(secretKey);
        Double amountFrom = Double.parseDouble(amount);
        Currency currencyFrom = findCurrencyByName(currencyFromName);
        Currency currencyTo = findCurrencyByName(currencyToName);
        Double currentRate = getRate(currencyFrom, currencyTo).getValue();
        Double amountTo = amountFrom * currentRate;
        withdrawMoney(secretKey, currencyFromName, amount);
        log.info("Снятие денег с пользователя {} в валюте {}" +
                        " в количестве: {}" + " было произведено успешно!",
                secretKey, currencyFromName, amount);
        topUpBalance(secretKey, amountTo.toString(), currencyToName);
        log.info("Начисление денег на счет пользователя {} в валюте {}" +
                        " в количестве: {}" + " было произведено успешно!",
                secretKey, currencyToName, amount);
        addTransaction(person.getWallet(), person.getWallet(), currencyFrom);
        log.info("Пользователь {} успешно обменял валюту {}" +
                        " на валюту {}" +
                        " в количестве: {}" +
                        " по курсу: {}",
                secretKey, currencyFromName, currencyToName, amount, currentRate);
        return amountTo;
    }

    @Override
    public Double topUpBalance(String secretKey, String amount, String currencyName) {
        log.info("Пользователь {} хочет пополнить свой баланс {}" +
                        " на количество: {}",
                secretKey, currencyName, amount);
        Currency currentCurrency = findCurrencyByName(currencyName);
        Person person = getUser(secretKey);
        Optional<CurrencyAmount> currentCurrencyAmount = getChosenCurrencyAmount(person, currentCurrency);
        Double totalSum = Double.parseDouble(amount);
        if (currentCurrencyAmount.isEmpty()) {

            CurrencyAmount currencyAmount = new CurrencyAmount();
            currencyAmount.setCurrency(currentCurrency);
            currencyAmount.setAmount(totalSum);
            currencyAmountRepository.save(currencyAmount);
            log.info("У пользователя {} не было данной валюты на счете, успешно добавлено" +
                            "и установлено необходимое количество!",
                    secretKey);
            person.getWallet().getCurrencies().add(currencyAmount);
        } else {
            Double previousAmount = currentCurrencyAmount.get().getAmount();
            currentCurrencyAmount.get().setAmount(totalSum + previousAmount);
            currencyAmountRepository.save(currentCurrencyAmount.get());
            totalSum = totalSum + previousAmount;
        }
        addTransaction(null, person.getWallet(), currentCurrency);
        log.info("Пользователь {} успешно пополнил свой баланс {}" +
                        " на количество: {}",
                secretKey, currencyName, amount);
        return totalSum;
    }

    @Override
    public UUID logUp(String name, String email) {
        log.info("Пользователь вызвал метод регистрации");
        if (userRepository.findByUserNameOrEmail(name, email).isPresent()) {
            log.error("Пользователь уже есть в системе!");
            throw new UserIsAlreadyLoggedUpException();
        }
        Person person = new Person();
        person.setUserName(name);
        person.setEmail(email);
        Wallet wallet = new Wallet();
        walletRepository.save(wallet);
        person.setWallet(wallet);
        userRepository.save(person);
        log.info("Пользователь {} с емеилом {} был успешно сохранен в базу данных" +
                        " и получил секретный ключ: {}",
                person.getUserName(), person.getEmail(), person.getSecretKey());
        return person.getSecretKey();
    }
}
