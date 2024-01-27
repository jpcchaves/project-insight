package com.insight.pontoapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonUtils {
    <T> String buildJsonResponse(T valueToConvert) throws JsonProcessingException;
}
