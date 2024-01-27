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

    public Duration calcularAtraso(LocalTime entrada) {
        LocalTime entradaEsperada = calcularEntradaEsperada(entrada);
        return Duration.between(entradaEsperada, entrada).abs();
    }

    public Duration calcularHoraExtra(LocalTime saida) {
        LocalTime saidaEsperada = calcularSaidaEsperada(saida);
        return Duration.between(saida, saidaEsperada).abs();
    }

    public Duration calcularCargaHoraria() {
        Duration manhaDuration = Duration.between(inicioManha, fimManha);

        Duration tardeDuration = Duration.between(inicioTarde, fimTarde);

        return manhaDuration.plus(tardeDuration);
    }

    public LocalTime calcularEntradaEsperada(LocalTime entrada) {
        if (entrada.isBefore(inicioManha)) {
            return inicioManha;
        } else if (entrada.isAfter(fimManha) && entrada.isBefore(inicioTarde)) {
            return inicioTarde;
        } else {
            return entrada;
        }
    }

    public Duration calcularDuracaoTotal() {
        LocalTime inicioManha = this.getInicioManha();
        LocalTime fimManha = this.getFimManha();
        LocalTime inicioTarde = this.getInicioTarde();
        LocalTime fimTarde = this.getFimTarde();

        Duration duracaoManha = Duration.between(inicioManha, fimManha);
        Duration duracaoTarde = Duration.between(PeriodoPonto.this.inicioTarde, fimTarde);

        return duracaoManha.plus(duracaoTarde);
    }

    public LocalTime calcularSaidaEsperada(LocalTime saida) {
        if (saida.isBefore(fimManha)) {
            return fimManha;
        } else if (saida.isAfter(inicioTarde) && saida.isBefore(fimTarde)) {
            return fimTarde;
        } else {
            return saida;
        }
    }
}
