package ru.shatalov.cryptotrading.exception;

public class NotEnoughMoneyException extends RuntimeException{
    public NotEnoughMoneyException() {
        super("На вашем счете недостаточно средств для данной операции");
    }
}
