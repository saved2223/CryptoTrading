package ru.shatalov.cryptotrading.exception;

public class InvalidSecretKeyException extends RuntimeException{
    public InvalidSecretKeyException() {
        super("Введен неверный секретный ключ!");
    }
}
