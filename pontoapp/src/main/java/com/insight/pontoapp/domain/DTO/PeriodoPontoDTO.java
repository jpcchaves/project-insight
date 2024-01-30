package com.insight.pontoapp.domain.DTO;

import java.time.LocalTime;

public class PeriodoPontoDTO {
    private LocalTime entradaPeriodo;
    private LocalTime saidaPeriodo;

    public PeriodoPontoDTO() {
    }

    public PeriodoPontoDTO(LocalTime entradaPeriodo,
                           LocalTime saidaPeriodo) {
        this.entradaPeriodo = entradaPeriodo;
        this.saidaPeriodo = saidaPeriodo;
    }

    public LocalTime getEntradaPeriodo() {
        return entradaPeriodo;
    }

    public void setEntradaPeriodo(LocalTime entradaPeriodo) {
        this.entradaPeriodo = entradaPeriodo;
    }

    public LocalTime getSaidaPeriodo() {
        return saidaPeriodo;
    }

    public void setSaidaPeriodo(LocalTime saidaPeriodo) {
        this.saidaPeriodo = saidaPeriodo;
    }
}
