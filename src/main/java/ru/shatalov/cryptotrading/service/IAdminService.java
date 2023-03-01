package ru.shatalov.cryptotrading.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IAdminService {
    Map<String, String> changeRateForBaseCurrencyFromStringMap(Map<String, String> map);

    Double getTotalAmountOfCurrency(String currencyName);

    Long getNumberOfTransactionsDependsOnDate(Date dateFrom, Date dateTo);

    List<UUID> getAllAdminsIds();
}
