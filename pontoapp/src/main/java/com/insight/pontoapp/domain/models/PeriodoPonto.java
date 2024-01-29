package com.insight.pontoapp.domain.models;

import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

public class PeriodoPonto {
    private UUID id = UUID.randomUUID();
    private LocalTime inicioManha;
    private LocalTime fimManha;
    private LocalTime inicioTarde;
    private LocalTime fimTarde;


    public PeriodoPonto() {
    }

    public PeriodoPonto(LocalTime inicioManha,
                        LocalTime fimManha,
                        LocalTime inicioTarde,
                        LocalTime fimTarde) {
        this.inicioManha = inicioManha;
        this.fimManha = fimManha;
        this.inicioTarde = inicioTarde;
        this.fimTarde = fimTarde;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalTime getInicioManha() {
        return inicioManha;
    }

    public void setInicioManha(LocalTime inicioManha) {
        this.inicioManha = inicioManha;
    }

    public LocalTime getFimManha() {
        return fimManha;
    }

    public void setFimManha(LocalTime fimManha) {
        this.fimManha = fimManha;
    }

    public LocalTime getInicioTarde() {
        return inicioTarde;
    }

    public void setInicioTarde(LocalTime inicioTarde) {
        this.inicioTarde = inicioTarde;
    }

    public LocalTime getFimTarde() {
        return fimTarde;
    }

    public void setFimTarde(LocalTime fimTarde) {
        this.fimTarde = fimTarde;
    }
    public Duration calcularCargaHoraria() {
        Duration manhaDuration = Duration.between(inicioManha, fimManha);

        Duration tardeDuration = Duration.between(inicioTarde, fimTarde);

        return manhaDuration.plus(tardeDuration);
    }
}
