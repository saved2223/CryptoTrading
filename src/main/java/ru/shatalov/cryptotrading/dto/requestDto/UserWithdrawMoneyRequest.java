package ru.shatalov.cryptotrading.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWithdrawMoneyRequest {
    private String secret_key;
    private String currency;
    private String count;
    private String credit_card;
    private String wallet;
}
