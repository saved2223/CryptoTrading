package ru.shatalov.cryptotrading.exception;

public class WrongDateFormatException extends RuntimeException {
    public WrongDateFormatException() {
        super("Неверный формат даты!");
    }
}
