package com.insight.pontoapp.utils.periodoPonto;

import com.insight.pontoapp.domain.models.PeriodoPonto;

import java.util.UUID;

public interface PeriodoPontoUtils {
    PeriodoPonto findById(UUID id) throws IllegalArgumentException;
}
