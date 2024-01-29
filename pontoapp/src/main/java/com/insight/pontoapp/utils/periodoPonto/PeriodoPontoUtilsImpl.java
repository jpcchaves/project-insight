package com.insight.pontoapp.utils.periodoPonto;

import com.insight.pontoapp.data.PeriodoPontoData;
import com.insight.pontoapp.domain.models.PeriodoPonto;

import java.util.UUID;

public class PeriodoPontoUtilsImpl implements PeriodoPontoUtils {

    @Override
    public PeriodoPonto findById(UUID id) throws IllegalArgumentException {
        return PeriodoPontoData
                .getPeriodoPonto()
                .stream()
                .filter(periodoPonto -> periodoPonto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Periodo nao encontrado com o ID informado"));
    }
}
