package com.insight.pontoapp.servlets;

import com.insight.pontoapp.data.PeriodoPontoData;
import com.insight.pontoapp.domain.models.PeriodoPonto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@WebServlet("/ponto")
public class PeriodoPontoServlet extends HttpServlet {

    private static final long serialVersionUID = 151634592284318466L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        String inicioManhaStr = request.getParameter("inicioManha");
        String saidaManhaStr = request.getParameter("saidaManha");
        String inicioTardeStr = request.getParameter("inicioTarde");
        String saidaTardeStr = request.getParameter("saidaTarde");

        LocalTime inicioManha = LocalTime.parse(inicioManhaStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime saidaManha = LocalTime.parse(saidaManhaStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime inicioTarde = LocalTime.parse(inicioTardeStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime saidaTarde = LocalTime.parse(saidaTardeStr, DateTimeFormatter.ofPattern("HH:mm"));

        PeriodoPonto periodoPonto = new PeriodoPonto(inicioManha, saidaManha, inicioTarde, saidaTarde);
        validateUserInput(periodoPonto);

        PeriodoPontoData.setPeriodoPonto(periodoPonto);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(buildJsonResponse(PeriodoPontoData.getPeriodoPonto()));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(buildJsonResponse(PeriodoPontoData.getPeriodoPonto()));
    }

    private String buildJsonResponse(PeriodoPonto ponto) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\": \"").append(ponto.getId()).append("\", ");
        json.append("\"inicioManha\": \"").append(ponto.getEntradaManha()).append("\", ");
        json.append("\"saidaManha\": \"").append(ponto.getSaidaManha()).append("\", ");
        json.append("\"inicioTarde\": \"").append(ponto.getEntradaTarde()).append("\", ");
        json.append("\"saidaTarde\": \"").append(ponto.getSaidaTarde()).append("\"");
        json.append("}");
        
        return json.toString();
    }


    private void validateUserInput(PeriodoPonto periodoPonto) {
        if (Objects.isNull(periodoPonto.getEntradaManha()) ||
                Objects.isNull(periodoPonto.getSaidaManha()) ||
                Objects.isNull(periodoPonto.getEntradaTarde()) ||
                Objects.isNull(periodoPonto.getSaidaTarde())
        ) {
            throw new IllegalArgumentException("O período inicial e final da manhã e tarde são obrigatórios");
        }
    }
}