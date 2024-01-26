package com.insight.pontoapp.data;

import com.insight.pontoapp.domain.models.PeriodoPonto;

public class PeriodoPontoData {
    private static PeriodoPonto periodoPonto = new PeriodoPonto();


    public PeriodoPontoData() {
    }

    public static void setPeriodoPonto(PeriodoPonto periodoPonto) {
        PeriodoPontoData.periodoPonto = periodoPonto;
    }

    public static PeriodoPonto getPeriodoPonto() {
        return periodoPonto;
    }
}
