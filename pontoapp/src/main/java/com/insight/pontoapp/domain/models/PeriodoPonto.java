package com.insight.pontoapp.domain.models;

import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

public class PeriodoPonto {
    private UUID id = UUID.randomUUID();
    private LocalTime entradaPeriodo;
    private LocalTime saidaPeriodo;

    public PeriodoPonto() {
    }

    public PeriodoPonto(LocalTime entradaPeriodo,
                        LocalTime saidaPeriodo) {
        this.entradaPeriodo = entradaPeriodo;
        this.saidaPeriodo = saidaPeriodo;
    }

    public PeriodoPonto(UUID id,
                        LocalTime entradaPeriodo,
                        LocalTime saidaPeriodo) {
        this.id = id;
        this.entradaPeriodo = entradaPeriodo;
        this.saidaPeriodo = saidaPeriodo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Duration calcularCargaHoraria() {
        return Duration.between(entradaPeriodo, saidaPeriodo);
    }
}
