package ru.shatalov.cryptotrading.exception;

public class UserIsAlreadyLoggedUpException extends RuntimeException {
    public UserIsAlreadyLoggedUpException() {
        super("Пользователь с таким никнеймом или email уже зарегестрирован!");
    }
}
