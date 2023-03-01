package ru.shatalov.cryptotrading.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException() {
        super("Неверный формат запроса!");
    }
}
