package com.insight.pontoapp.data;

import com.insight.pontoapp.domain.models.Marcacao;

import java.util.ArrayList;
import java.util.List;

public class MarcacoesData {

    private static List<Marcacao> marcacoesData = new ArrayList<>();

    public MarcacoesData() {
    }

    public static List<Marcacao> getMarcacoesData() {
        return marcacoesData;
    }
}
