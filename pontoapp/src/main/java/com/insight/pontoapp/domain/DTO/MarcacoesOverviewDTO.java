package com.insight.pontoapp.domain.DTO;

public class MarcacoesOverviewDTO {
    private String atraso;
    private String horaExtra;
    private String saldo;

    public MarcacoesOverviewDTO() {
    }

    public MarcacoesOverviewDTO(String atraso,
                                String horaExtra,
                                String saldo) {
        this.atraso = atraso;
        this.horaExtra = horaExtra;
        this.saldo = saldo;
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

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
