package com.insight.pontoapp.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.insight.pontoapp.data.MarcacoesData;
import com.insight.pontoapp.data.PeriodoPontoData;
import com.insight.pontoapp.domain.DTO.MarcacaoRequestDTO;
import com.insight.pontoapp.domain.DTO.MarcacaoResponseDTO;
import com.insight.pontoapp.domain.models.Marcacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        objectMapper.setDateFormat(new SimpleDateFormat("HH:mm"));

        objectMapper.registerModule(new SimpleModule().addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter)));

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
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        objectMapper.setDateFormat(new SimpleDateFormat("HH:mm"));

        objectMapper.registerModule(new SimpleModule().addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter)));

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<MarcacaoResponseDTO> marcacaoResponseDTO = new ArrayList<>();

        MarcacoesData
                .getMarcacoesData()
                .forEach(
                        marcacao -> marcacaoResponseDTO.add(
                                new MarcacaoResponseDTO(
                                        marcacao.getId(),
                                        marcacao.getEntradaManha(),
                                        marcacao.getSaidaManha(),
                                        marcacao.getEntradaTarde(),
                                        marcacao.getSaidaTarde(),
                                        marcacao.getAtrasoFormatado(),
                                        marcacao.getHoraExtraFormatada())
                        )
                );

        out.print(buildJsonResponse(marcacaoResponseDTO));
    }


    private <T> String buildJsonResponse(T valueToConvert) throws JsonProcessingException {
        return objectMapper.writeValueAsString(valueToConvert);
    }
}
