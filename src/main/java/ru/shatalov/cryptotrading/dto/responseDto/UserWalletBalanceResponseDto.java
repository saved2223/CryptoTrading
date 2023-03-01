package ru.shatalov.cryptotrading.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserWalletBalanceResponseDto {

    private final Map<String, String> params = new LinkedHashMap<>();

    public UserWalletBalanceResponseDto(Map<String, Double> map) {
        setParams(map);
    }

    @JsonAnyGetter
    public Map<String, String> getParams() {
        return params;
    }

    private void setParams(Map<String, Double> map) {
        for (String key : map.keySet()) {
            params.put(key, map.get(key).toString());
        }
    }
}
