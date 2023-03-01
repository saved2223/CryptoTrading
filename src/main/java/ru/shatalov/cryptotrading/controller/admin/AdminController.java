package ru.shatalov.cryptotrading.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shatalov.cryptotrading.dto.responseDto.StringResponseDto;
import ru.shatalov.cryptotrading.exception.BadRequestException;
import ru.shatalov.cryptotrading.exception.UserAccessDeniedException;
import ru.shatalov.cryptotrading.exception.WrongDateFormatException;
import ru.shatalov.cryptotrading.service.IAdminService;
import ru.shatalov.cryptotrading.utils.ParsingUtils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final IAdminService adminService;
    private final ParsingUtils parsingUtils;

    public AdminController(IAdminService adminService, ParsingUtils parsingUtils) {
        this.adminService = adminService;
        this.parsingUtils = parsingUtils;
    }

    @PostMapping(value = "/changeRate")
    public ResponseEntity<?> changeRate(@RequestBody String request) {
        Map<String, String> requestMap = parsingUtils.parseStringJsonToMap(request);
        if (!requestMap.containsKey("secret_key") ||
                !requestMap.containsKey("base_currency")) {
            throw new BadRequestException();
        }
        checkAccess(requestMap.remove("secret_key"));
        Map<String, String> responseMap = adminService
                .changeRateForBaseCurrencyFromStringMap(requestMap);
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping(value = "/totalAmountOfCurrency")
    public ResponseEntity<?> getTotalAmountOfCurrency(@RequestParam("secret_key") String secretKey,
                                                      @RequestParam("currency") String currencyName) {
        checkAccess(secretKey);
        return ResponseEntity.ok(new StringResponseDto(currencyName,
                adminService.getTotalAmountOfCurrency(currencyName).toString()));
    }

    @GetMapping(value = "/transactions")
    public ResponseEntity<?> getTransactions(@RequestParam("secret_key") String secretKey,
                                             @RequestParam("date_from") String dateFromString,
                                             @RequestParam("date_to") String dateToString) {
        checkAccess(secretKey);
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = new Date(format.parse(dateFromString).getTime());
            dateTo = new Date(format.parse(dateToString).getTime());
        } catch (ParseException e) {
            throw new WrongDateFormatException();
        }
        return ResponseEntity.ok(new StringResponseDto("transaction_count",
                adminService.getNumberOfTransactionsDependsOnDate(dateFrom, dateTo).toString()));
    }

    private void checkAccess(String secretKey) {
        if (!adminService.getAllAdminsIds().contains(UUID.fromString(secretKey))) {
            throw new UserAccessDeniedException();
        }
    }
}
