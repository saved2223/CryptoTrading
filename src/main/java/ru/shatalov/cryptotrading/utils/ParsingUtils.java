package ru.shatalov.cryptotrading.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.shatalov.cryptotrading.exception.BadRequestException;

import java.util.HashMap;
import java.util.Map;

@Component
public class ParsingUtils {

    public Map<String, String> parseStringJsonToMap(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map;
        try {
            map = objectMapper.readValue(json, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new BadRequestException();
        }
        return map;
    }
}
