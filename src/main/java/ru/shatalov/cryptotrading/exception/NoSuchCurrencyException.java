package ru.shatalov.cryptotrading.exception;

public class NoSuchCurrencyException extends RuntimeException{
    public NoSuchCurrencyException() {
        super("Введенная вами валюта не представлена на этой площадке");
    }
}
