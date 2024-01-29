package com.insight.pontoapp.domain.DTO;

import java.time.LocalTime;
import java.util.UUID;

public class PeriodoPontoRequestDTO {
    private UUID id;
    private LocalTime inicioManha;
    private LocalTime fimManha;
    private LocalTime inicioTarde;
    private LocalTime fimTarde;

    public PeriodoPontoRequestDTO() {
    }

    public PeriodoPontoRequestDTO(UUID id,
                                  LocalTime inicioManha,
                                  LocalTime fimManha,
                                  LocalTime inicioTarde,
                                  LocalTime fimTarde) {
        this.id = id;
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
}
