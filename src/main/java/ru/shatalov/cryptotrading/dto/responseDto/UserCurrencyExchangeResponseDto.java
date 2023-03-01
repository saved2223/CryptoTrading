package ru.shatalov.cryptotrading.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCurrencyExchangeResponseDto {
    private String currency_from;
    private String currency_to;
    private Double amount_from;
    private Double amount_to;
}
