package ru.shatalov.cryptotrading.service;

import java.util.Map;
import java.util.UUID;

public interface IUserService {
    UUID logUp(String name, String email);

    Map<String, Double> getWalletBalance(String secretKey);

    Double topUpBalance(String secretKey, String amount, String currencyName);

    Double exchangeCurrency(String secretKey,
                            String currencyFromName,
                            String currencyToName,
                            String amount);

    Double withdrawMoney(String secretKey,
                         String currencyName,
                         String count);

    Map<String, String> getActualRates(String secretKey, String currencyFromName);
}
