package com.insight.pontoapp.domain.DTO;

import java.time.LocalTime;

public class PeriodoPontoDTO {
    private LocalTime entradaManha;
    private LocalTime saidaManha;
    private LocalTime entradaTarde;
    private LocalTime saidaTarde;

    public PeriodoPontoDTO() {
    }

    public PeriodoPontoDTO(LocalTime entradaManha,
                           LocalTime saidaManha,
                           LocalTime entradaTarde,
                           LocalTime saidaTarde) {
        this.entradaManha = entradaManha;
        this.saidaManha = saidaManha;
        this.entradaTarde = entradaTarde;
        this.saidaTarde = saidaTarde;
    }

    public LocalTime getEntradaManha() {
        return entradaManha;
    }

    public void setEntradaManha(LocalTime entradaManha) {
        this.entradaManha = entradaManha;
    }

    public LocalTime getSaidaManha() {
        return saidaManha;
    }

    public void setSaidaManha(LocalTime saidaManha) {
        this.saidaManha = saidaManha;
    }

    public LocalTime getEntradaTarde() {
        return entradaTarde;
    }

    public void setEntradaTarde(LocalTime entradaTarde) {
        this.entradaTarde = entradaTarde;
    }

    public LocalTime getSaidaTarde() {
        return saidaTarde;
    }

    public void setSaidaTarde(LocalTime saidaTarde) {
        this.saidaTarde = saidaTarde;
    }
}
