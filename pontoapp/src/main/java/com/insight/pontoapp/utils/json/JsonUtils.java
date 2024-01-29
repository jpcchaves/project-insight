package com.insight.pontoapp.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonUtils {
    <T> String buildJsonResponse(T valueToConvert) throws JsonProcessingException;
}
