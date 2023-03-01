package ru.shatalov.cryptotrading.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTopUpRequestDto {
    private String secret_key;
    private String currencyFullName;
    private String currencyShortName;
    private String amount;
}
