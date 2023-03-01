package ru.shatalov.cryptotrading.controller.user;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shatalov.cryptotrading.dto.requestDto.UserCurrencyExchangeRequestDto;
import ru.shatalov.cryptotrading.dto.requestDto.UserLogUpRequestDto;
import ru.shatalov.cryptotrading.dto.requestDto.UserTopUpRequestDto;
import ru.shatalov.cryptotrading.dto.requestDto.UserWithdrawMoneyRequest;
import ru.shatalov.cryptotrading.dto.responseDto.SecretKeyDto;
import ru.shatalov.cryptotrading.dto.responseDto.StringResponseDto;
import ru.shatalov.cryptotrading.dto.responseDto.UserCurrencyExchangeResponseDto;
import ru.shatalov.cryptotrading.dto.responseDto.UserWalletBalanceResponseDto;
import ru.shatalov.cryptotrading.exception.BadRequestException;
import ru.shatalov.cryptotrading.service.IUserService;
import ru.shatalov.cryptotrading.utils.ParsingUtils;

import java.util.Map;
import java.util.UUID;


@RestController
public class UserController {

    private final IUserService userService;
    private final ParsingUtils parsingUtils;

    public UserController(IUserService userService, ParsingUtils parsingUtils) {
        this.userService = userService;
        this.parsingUtils = parsingUtils;
    }

    @GetMapping(value = "/currencyRates")
    public ResponseEntity<?> currencyRates(@RequestParam("secret_key") String secretKey,
                                           @RequestParam("currency") String currencyName) {
        return ResponseEntity.ok(userService.getActualRates(secretKey, currencyName));
    }

    @PostMapping(value = "/logUp")
    public ResponseEntity<?> logUp(@RequestBody UserLogUpRequestDto userLogUpRequestDto) {
        UUID secretKeyUser = userService.logUp(userLogUpRequestDto.getUsername(), userLogUpRequestDto.getEmail());
        return ResponseEntity.ok(new SecretKeyDto(secretKeyUser.toString()));
    }

    @GetMapping(value = "/walletBalance")
    public ResponseEntity<?> getWalletBalance(@RequestParam("secret_key") String secretKey) {
        Map<String, Double> responseMap = userService.getWalletBalance(secretKey);
        return ResponseEntity.ok(new UserWalletBalanceResponseDto(responseMap));
    }

    @PostMapping(value = "/topUpWallet")
    public ResponseEntity<?> topUpWallet(@RequestBody String json) {
        Map<String, String> requestMap = parsingUtils.parseStringJsonToMap(json);
        UserTopUpRequestDto userTopUpRequestDto = getTopUpRequestDtoFromStringMap(requestMap);
        Double totalAmount = userService
                .topUpBalance(userTopUpRequestDto.getSecret_key(),
                        userTopUpRequestDto.getAmount(),
                        userTopUpRequestDto.getCurrencyShortName());
        return ResponseEntity.ok(new StringResponseDto(userTopUpRequestDto.getCurrencyFullName(),
                totalAmount.toString()));
    }

    @PostMapping(value = "/exchangeCurrency")
    public ResponseEntity<?> exchangeCurrency(
            @RequestBody UserCurrencyExchangeRequestDto userCurrencyExchangeRequestDto) {
        Double amountTo = userService.exchangeCurrency(
                userCurrencyExchangeRequestDto.getSecret_key(),
                userCurrencyExchangeRequestDto.getCurrency_from(),
                userCurrencyExchangeRequestDto.getCurrency_to(),
                userCurrencyExchangeRequestDto.getAmount());
        UserCurrencyExchangeResponseDto responseDto = new UserCurrencyExchangeResponseDto(
                userCurrencyExchangeRequestDto.getCurrency_from(),
                userCurrencyExchangeRequestDto.getCurrency_to(),
                Double.parseDouble(userCurrencyExchangeRequestDto.getAmount()),
                amountTo);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping(value = "/withdrawMoney")
    public ResponseEntity<?> withdrawMoney(@RequestBody UserWithdrawMoneyRequest request) {
        Double moneyLeft = userService.withdrawMoney(request.getSecret_key(), request.getCurrency(),
                request.getCount());
        return ResponseEntity.ok(new StringResponseDto(request.getCurrency().concat("_wallet"),
                moneyLeft.toString()));
    }

    private UserTopUpRequestDto getTopUpRequestDtoFromStringMap(
            Map<String, String> requestMap) {
        if (!requestMap.containsKey("secret_key") || requestMap.size() > 2) {
            throw new BadRequestException();
        } //весь колхоз ниже ради того, чтобы можно было пополнять кошельки любой валюты
        String secretKey = requestMap.remove("secret_key");
        String currencyNameOld = requestMap
                .keySet()
                .stream()
                .toList()
                .get(0);
        String currencyName = currencyNameOld.substring(0, currencyNameOld.lastIndexOf("_"));
        String amount = requestMap.get(currencyNameOld);
        return new UserTopUpRequestDto(secretKey, currencyNameOld, currencyName, amount);
    }
}
