package ru.shatalov.cryptotrading.exception;

public class NothingInWalletException extends RuntimeException{
    public NothingInWalletException() {
        super("На вашем кошельке нет ни одной валюты");
    }
}
