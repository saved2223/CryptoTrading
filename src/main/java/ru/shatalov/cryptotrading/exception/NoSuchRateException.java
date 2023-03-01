package ru.shatalov.cryptotrading.exception;

public class NoSuchRateException extends RuntimeException{
    public NoSuchRateException() {
        super("Извините, курс для данных валют еще не установлен");
    }
}
