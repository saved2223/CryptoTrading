package ru.shatalov.cryptotrading.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    private String message;
}
