package com.insight.pontoapp.domain.DTO;

import java.time.LocalTime;
import java.util.UUID;

public class MarcacaoResponseDTO {
    private UUID id;
    private LocalTime entradaManha;
    private LocalTime saidaManha;
    private LocalTime entradaTarde;
    private LocalTime saidaTarde;
    private String atraso;
    private String horaExtra;

    public MarcacaoResponseDTO() {
    }

    public MarcacaoResponseDTO(UUID id,
                               LocalTime entradaManha,
                               LocalTime saidaManha,
                               LocalTime entradaTarde,
                               LocalTime saidaTarde,
                               String atraso,
                               String horaExtra) {
        this.id = id;
        this.entradaManha = entradaManha;
        this.saidaManha = saidaManha;
        this.entradaTarde = entradaTarde;
        this.saidaTarde = saidaTarde;
        this.atraso = atraso;
        this.horaExtra = horaExtra;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getAtraso() {
        return atraso;
    }

    public void setAtraso(String atraso) {
        this.atraso = atraso;
    }

    public String getHoraExtra() {
        return horaExtra;
    }

    public void setHoraExtra(String horaExtra) {
        this.horaExtra = horaExtra;
    }
}
