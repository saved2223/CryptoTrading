package ru.shatalov.cryptotrading.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class StringResponseDto {
    private final Map<String, String> params = new LinkedHashMap<>();

    public StringResponseDto(String variableName, String value) {
        setParams(variableName, value);
    }

    @JsonAnyGetter
    public Map<String, String> getParams() {
        return params;
    }

    private void setParams(String variableName, String value) {
        params.put(variableName, value);
    }

}
