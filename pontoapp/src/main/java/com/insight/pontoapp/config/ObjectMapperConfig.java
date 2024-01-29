package com.insight.pontoapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ObjectMapperConfig implements Serializable {
    private static final long serialVersionUID = 6300070374795355048L;

    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        objectMapper.setDateFormat(new SimpleDateFormat("HH:mm"));

        objectMapper.registerModule(new SimpleModule().addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter)));

        return objectMapper;
    }
}
