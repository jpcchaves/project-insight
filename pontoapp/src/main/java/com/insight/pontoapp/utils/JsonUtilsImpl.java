package com.insight.pontoapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.insight.pontoapp.config.ObjectMapperConfig;

public class JsonUtilsImpl implements JsonUtils {
    private final ObjectMapperConfig mapperConfig = new ObjectMapperConfig();

    @Override
    public <T> String buildJsonResponse(T valueToConvert) throws JsonProcessingException {
        return mapperConfig.objectMapper().writeValueAsString(valueToConvert);
    }
}
