package com.insight.pontoapp.domain.models;

import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

public class PeriodoPonto {
    private UUID id = UUID.randomUUID();
    private LocalTime entradaManha;
    private LocalTime saidaManha;
    private LocalTime entradaTarde;
    private LocalTime saidaTarde;


    public PeriodoPonto() {
    }

    public PeriodoPonto(LocalTime entradaManha,
                        LocalTime saidaManha,
                        LocalTime entradaTarde,
                        LocalTime saidaTarde) {
        this.entradaManha = entradaManha;
        this.saidaManha = saidaManha;
        this.entradaTarde = entradaTarde;
        this.saidaTarde = saidaTarde;
    }

    public PeriodoPonto(PeriodoPonto periodoPonto) {
        this.entradaManha = periodoPonto.getEntradaManha();
        this.saidaManha = periodoPonto.getSaidaManha();
        this.entradaTarde = periodoPonto.getEntradaTarde();
        this.saidaTarde = periodoPonto.getSaidaTarde();
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

    public Duration calcularAtraso(LocalTime entrada) {
        LocalTime entradaEsperada = calcularEntradaEsperada(entrada);
        return Duration.between(entradaEsperada, entrada).abs();
    }

    public Duration calcularHoraExtra(LocalTime saida) {
        LocalTime saidaEsperada = calcularSaidaEsperada(saida);
        return Duration.between(saida, saidaEsperada).abs();
    }

    public Duration calcularCargaHoraria() {
        Duration manhaDuration = Duration.between(entradaManha, saidaManha);

        Duration tardeDuration = Duration.between(entradaTarde, saidaTarde);

        return manhaDuration.plus(tardeDuration);
    }

    public LocalTime calcularEntradaEsperada(LocalTime entrada) {
        if (entrada.isBefore(entradaManha)) {
            return entradaManha;
        } else if (entrada.isAfter(saidaManha) && entrada.isBefore(entradaTarde)) {
            return entradaTarde;
        } else {
            return entrada;
        }
    }

    public Duration calcularDuracaoTotal() {
        LocalTime entradaManha = this.getEntradaManha();
        LocalTime saidaManha = this.getSaidaManha();
        LocalTime entradaTarde = this.getEntradaTarde();
        LocalTime saidaTarde = this.getSaidaTarde();

        Duration duracaoManha = Duration.between(entradaManha, saidaManha);
        Duration duracaoTarde = Duration.between(entradaTarde, saidaTarde);

        return duracaoManha.plus(duracaoTarde);
    }

    public LocalTime calcularSaidaEsperada(LocalTime saida) {
        if (saida.isBefore(saidaManha)) {
            return saidaManha;
        } else if (saida.isAfter(entradaTarde) && saida.isBefore(saidaTarde)) {
            return saidaTarde;
        } else {
            return saida;
        }
    }
}
