package com.insight.pontoapp.domain.DTO;

import java.time.LocalTime;
import java.util.UUID;

public class PeriodoPontoRequestDTO {
    private UUID id;
    private LocalTime entradaPeriodo;
    private LocalTime saidaPeriodo;

    public PeriodoPontoRequestDTO() {
    }

    public PeriodoPontoRequestDTO(UUID id,
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
}
