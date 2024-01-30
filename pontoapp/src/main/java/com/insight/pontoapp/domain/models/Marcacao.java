package com.insight.pontoapp.domain.models;

import java.time.LocalTime;
import java.util.UUID;

public class Marcacao {
    private UUID id = UUID.randomUUID();
    private LocalTime entrada;
    private LocalTime saida;


    public Marcacao() {
    }

    public Marcacao(LocalTime entrada,
                    LocalTime saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

    public Marcacao(UUID id,
                    LocalTime entrada,
                    LocalTime saida) {
        this.id = id;
        this.entrada = entrada;
        this.saida = saida;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
