package ru.shatalov.cryptotrading.exception;

public class UserAccessDeniedException extends RuntimeException{
    public UserAccessDeniedException() {
        super("Данное действие может выполнить только администратор!");
    }
}
