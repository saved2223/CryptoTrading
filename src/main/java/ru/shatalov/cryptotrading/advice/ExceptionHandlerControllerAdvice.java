package ru.shatalov.cryptotrading.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.shatalov.cryptotrading.dto.responseDto.ErrorDto;
import ru.shatalov.cryptotrading.exception.*;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(UserIsAlreadyLoggedUpException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorDto handleUserIsAlreadyLoggedUpException(UserIsAlreadyLoggedUpException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(UserAccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDto handleUserAccessDeniedException(UserAccessDeniedException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(NoSuchCurrencyException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleNoSuchCurrencyException(NoSuchCurrencyException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(NothingInWalletException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleNothingInWalletException(NothingInWalletException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(InvalidSecretKeyException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorDto handleInvalidSecretKeyException(InvalidSecretKeyException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleBadRequestException(BadRequestException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorDto handleNotEnoughMoneyException(NotEnoughMoneyException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(NoSuchRateException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleNoSuchRateException(NoSuchRateException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(WrongDateFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleWrongDateFormatException(WrongDateFormatException e) {
        return new ErrorDto(e.getMessage());
    }
}
