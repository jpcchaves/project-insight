package com.insight.pontoapp.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.insight.pontoapp.data.MarcacoesData;
import com.insight.pontoapp.data.PeriodoPontoData;
import com.insight.pontoapp.domain.DTO.MarcacaoRequestDTO;
import com.insight.pontoapp.domain.models.Marcacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/marcacoes")
public class MarcacoesServlet extends HttpServlet {
    private static final long serialVersionUID = 7302329288889146232L;
    ObjectMapper objectMapper = new ObjectMapper();

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        objectMapper.registerModule(new JavaTimeModule());

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        MarcacaoRequestDTO marcacaoJson = objectMapper.readValue(requestBody, MarcacaoRequestDTO.class);

        String entradaStr = marcacaoJson.getEntradaManha().toString();
        String saidaStr = marcacaoJson.getSaidaManha().toString();
        String entradaTardeStr = marcacaoJson.getEntradaTarde().toString();
        String saidaTardeStr = marcacaoJson.getSaidaTarde().toString();

        LocalTime entradaManha = LocalTime.parse(entradaStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime saidaManha = LocalTime.parse(saidaStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime entradaTarde = LocalTime.parse(entradaTardeStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime saidaTarde = LocalTime.parse(saidaTardeStr, DateTimeFormatter.ofPattern("HH:mm"));

        Marcacao marcacao = new Marcacao(entradaManha, saidaManha, entradaTarde, saidaTarde);
        MarcacoesData.getMarcacoesData().add(marcacao);

        marcacao.calcularAtrasoEHoraExtra(PeriodoPontoData.getPeriodoPonto());

        request.setAttribute("marcacoes", MarcacoesData.getMarcacoesData());

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(buildJsonResponse(MarcacoesData.getMarcacoesData()));
    }

    
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(buildJsonResponse(MarcacoesData.getMarcacoesData()));
    }


    private String buildJsonResponse(List<Marcacao> marcacoes) {
        StringBuilder json = new StringBuilder();
        json.append("[");

        for (Marcacao marcacao : marcacoes) {
            json.append("{");
            json.append("\"id\": \"").append(marcacao.getId()).append("\", ");
            json.append("\"inicioManha\": \"").append(marcacao.getEntradaManha()).append("\", ");
            json.append("\"saidaManha\": \"").append(marcacao.getSaidaManha()).append("\", ");
            json.append("\"inicioTarde\": \"").append(marcacao.getEntradaTarde()).append("\", ");
            json.append("\"saidaTarde\": \"").append(marcacao.getSaidaTarde()).append("\", ");
            json.append("\"atraso\": \"").append(marcacao.getAtrasoFormatado()).append("\", ");
            json.append("\"horaExtra\": \"").append(marcacao.getHoraExtraFormatada()).append("\"");
            json.append("},");
        }

        if (!marcacoes.isEmpty()) {
            json.setLength(json.length() - 1);
        }

        json.append("]");

        return json.toString();
    }
}
