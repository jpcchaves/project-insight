package com.insight.pontoapp.domain.DTO;

import java.time.LocalTime;

public class MarcacaoRequestDTO {
    private LocalTime entrada;
    private LocalTime saida;

    public MarcacaoRequestDTO() {
    }

    public MarcacaoRequestDTO(LocalTime entrada,
                              LocalTime saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

    public LocalTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalTime entrada) {
        this.entrada = entrada;
    }

    public LocalTime getSaida() {
        return saida;
    }

    public void setSaida(LocalTime saida) {
        this.saida = saida;
    }
}
