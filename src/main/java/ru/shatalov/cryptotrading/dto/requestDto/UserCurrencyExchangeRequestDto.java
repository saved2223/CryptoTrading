package ru.shatalov.cryptotrading.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCurrencyExchangeRequestDto {
    private String secret_key;
    private String currency_from;
    private String currency_to;
    private String amount;
}
