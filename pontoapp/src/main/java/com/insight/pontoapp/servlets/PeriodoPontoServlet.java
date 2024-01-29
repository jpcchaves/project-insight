package com.insight.pontoapp.servlets;

import com.insight.pontoapp.config.ObjectMapperConfig;
import com.insight.pontoapp.data.PeriodoPontoData;
import com.insight.pontoapp.domain.DTO.PeriodoPontoDTO;
import com.insight.pontoapp.domain.DTO.ServletMessageResponse;
import com.insight.pontoapp.domain.models.PeriodoPonto;
import com.insight.pontoapp.utils.JsonUtils;
import com.insight.pontoapp.utils.JsonUtilsImpl;
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
import java.util.stream.Collectors;


@WebServlet("/ponto")
public class PeriodoPontoServlet extends HttpServlet {
    private static final long serialVersionUID = 151634592284318466L;
    private final JsonUtils jsonUtils = new JsonUtilsImpl();
    private final ObjectMapperConfig mapperConfig = new ObjectMapperConfig();

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        validatePeriodoPontoLimit();

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        PeriodoPontoDTO periodoPontoJson = mapperConfig.objectMapper().readValue(requestBody, PeriodoPontoDTO.class);

        validateUserInput(periodoPontoJson);

        String inicioManhaStr = periodoPontoJson.getInicioManha().toString();
        String saidaManhaStr = periodoPontoJson.getFimManha().toString();
        String inicioTardeStr = periodoPontoJson.getInicioTarde().toString();
        String saidaTardeStr = periodoPontoJson.getFimTarde().toString();

        LocalTime inicioManha = LocalTime.parse(inicioManhaStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime saidaManha = LocalTime.parse(saidaManhaStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime inicioTarde = LocalTime.parse(inicioTardeStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime saidaTarde = LocalTime.parse(saidaTardeStr, DateTimeFormatter.ofPattern("HH:mm"));

        PeriodoPonto periodoPonto = new PeriodoPonto(inicioManha, saidaManha, inicioTarde, saidaTarde);

        PeriodoPontoData.getPeriodoPonto().add(periodoPonto);

        PrintWriter out = response.getWriter();
        out.print(jsonUtils.buildJsonResponse(new ServletMessageResponse("Periodo do ponto salvo com sucesso!")));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonUtils.buildJsonResponse(PeriodoPontoData.getPeriodoPonto()));
    }

    private void validatePeriodoPontoLimit() {
        int PERIODO_PONTO_MAX_LIMIT = 3;

        if(PeriodoPontoData.getPeriodoPonto().size() >= PERIODO_PONTO_MAX_LIMIT) {
            throw new IllegalArgumentException("Limite de periodos atingido!");
        }
    }

    private void validateUserInput(PeriodoPontoDTO periodoPonto) {
        if (Objects.isNull(periodoPonto.getInicioManha()) ||
                Objects.isNull(periodoPonto.getFimManha()) ||
                Objects.isNull(periodoPonto.getInicioTarde()) ||
                Objects.isNull(periodoPonto.getFimTarde())
        ) {
            throw new IllegalArgumentException("O período inicial e final da manhã e tarde são obrigatórios");
        }
    }
}