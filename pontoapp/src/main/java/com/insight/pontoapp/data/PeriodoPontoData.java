package com.insight.pontoapp.data;

import com.insight.pontoapp.domain.models.PeriodoPonto;

import java.util.ArrayList;
import java.util.List;

public class PeriodoPontoData {
    private static List<PeriodoPonto> periodoPonto = new ArrayList<>();


    public PeriodoPontoData() {
    }

    public static List<PeriodoPonto> getPeriodoPonto() {
        return periodoPonto;
    }

}
