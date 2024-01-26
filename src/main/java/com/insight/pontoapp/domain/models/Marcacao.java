package com.insight.pontoapp.domain.models;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Marcacao {
    private UUID id = UUID.randomUUID();
    private LocalTime entradaManha;
    private LocalTime saidaManha;
    private LocalTime entradaTarde;
    private LocalTime saidaTarde;
    private Duration atraso;
    private Duration horaExtra;
    private String atrasoFormatado;
    private String horaExtraFormatada;


    public Marcacao() {
    }

    public Marcacao(LocalTime entradaManha,
                    LocalTime saidaManha,
                    LocalTime entradaTarde,
                    LocalTime saidaTarde) {
        this.entradaManha = entradaManha;
        this.saidaManha = saidaManha;
        this.entradaTarde = entradaTarde;
        this.saidaTarde = saidaTarde;
    }

    public Marcacao(LocalTime entradaManha,
                    LocalTime saidaManha,
                    LocalTime entradaTarde,
                    LocalTime saidaTarde,
                    Duration atraso,
                    Duration horaExtra) {
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

    public Duration getAtraso() {
        return atraso;
    }

    public void setAtraso(Duration atraso) {
        this.atraso = atraso;
    }

    public Duration getHoraExtra() {
        return horaExtra;
    }

    public void setHoraExtra(Duration horaExtra) {
        this.horaExtra = horaExtra;
    }

    public String getAtrasoFormatado() {
        return atrasoFormatado;
    }

    public String getHoraExtraFormatada() {
        return horaExtraFormatada;
    }

    public void calcularAtrasoEHoraExtra(PeriodoPonto periodoPonto) {
        LocalTime entradaManha = getEntradaManha();
        LocalTime saidaManha = getSaidaManha();
        LocalTime entradaTarde = getEntradaTarde();
        LocalTime saidaTarde = getSaidaTarde();

        atraso = Duration.ZERO;
        horaExtra = Duration.ZERO;

        Duration cargaHorariaDiaria = periodoPonto.calcularCargaHoraria();

        Duration totalTrabalhado = Duration.ZERO;

        Duration totalManha = calcularDiferenca(entradaManha, saidaManha);
        totalTrabalhado = totalTrabalhado.plus(totalManha);

        Duration totalTarde = calcularDiferenca(entradaTarde, saidaTarde);
        totalTrabalhado = totalTrabalhado.plus(totalTarde);

        if (totalTrabalhado.compareTo(cargaHorariaDiaria) > 0) {
            horaExtra = totalTrabalhado.minus(cargaHorariaDiaria);
        } else {
            atraso = cargaHorariaDiaria.minus(totalTrabalhado);
        }

        atrasoFormatado = formatDuration(atraso);
        horaExtraFormatada = formatDuration(horaExtra);
    }

    private String formatDuration(Duration duration) {
        LocalTime localTime = LocalTime.MIDNIGHT.plus(duration);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return localTime.format(formatter);
    }

    private Duration calcularDiferenca(LocalTime inicio,
                                       LocalTime fim) {
        return Duration.between(inicio, fim);
    }
}
