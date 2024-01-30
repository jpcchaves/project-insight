package com.insight.pontoapp.servlets;

import com.insight.pontoapp.config.ObjectMapperConfig;
import com.insight.pontoapp.data.PeriodoPontoData;
import com.insight.pontoapp.domain.DTO.PeriodoPontoDTO;
import com.insight.pontoapp.domain.DTO.PeriodoPontoRequestDTO;
import com.insight.pontoapp.domain.DTO.ServletMessageResponse;
import com.insight.pontoapp.domain.models.PeriodoPonto;
import com.insight.pontoapp.utils.json.JsonUtils;
import com.insight.pontoapp.utils.json.JsonUtilsImpl;
import com.insight.pontoapp.utils.periodoPonto.PeriodoPontoUtils;
import com.insight.pontoapp.utils.periodoPonto.PeriodoPontoUtilsImpl;
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
import java.util.UUID;
import java.util.stream.Collectors;


@WebServlet("/ponto")
public class PeriodoPontoServlet extends HttpServlet {
    private static final long serialVersionUID = 151634592284318466L;
    private final JsonUtils jsonUtils = new JsonUtilsImpl();
    private final ObjectMapperConfig mapperConfig = new ObjectMapperConfig();
    private final PeriodoPontoUtils periodoPontoUtils = new PeriodoPontoUtilsImpl();

    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.addHeader("Access-Control-Allow-Origin", "*");

        String periodoPontoId = request.getParameter("id");
        UUID periodoUUID = UUID.fromString(periodoPontoId);

        deleteById(periodoUUID);

        response.setContentType("application/json");
        out.print(jsonUtils.buildJsonResponse(new ServletMessageResponse("Horario apagado com sucesso!")));
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        validatePeriodoPontoLimit();

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        PeriodoPontoDTO periodoPontoJson = mapperConfig.objectMapper().readValue(requestBody, PeriodoPontoDTO.class);

        validateUserInput(
                periodoPontoJson.getEntradaPeriodo(),
                periodoPontoJson.getSaidaPeriodo()
        );

        String inicioManhaStr = periodoPontoJson.getEntradaPeriodo().toString();
        String saidaManhaStr = periodoPontoJson.getSaidaPeriodo().toString();

        LocalTime inicioManha = LocalTime.parse(inicioManhaStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime saidaManha = LocalTime.parse(saidaManhaStr, DateTimeFormatter.ofPattern("HH:mm"));

        PeriodoPonto periodoPonto = new PeriodoPonto(inicioManha, saidaManha);

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

        String periodoPontoid = request.getParameter("id");

        if (periodoPontoid != null && !periodoPontoid.isEmpty()) {
            PeriodoPonto periodoPonto = periodoPontoUtils.findById(UUID.fromString(periodoPontoid));
            out.print(jsonUtils.buildJsonResponse(periodoPonto));
            return;
        }

        out.print(jsonUtils.buildJsonResponse(PeriodoPontoData.getPeriodoPonto()));
    }

    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");


        PrintWriter out = response.getWriter();
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        PeriodoPontoRequestDTO periodoPontoDTO = mapperConfig.objectMapper().readValue(requestBody, PeriodoPontoRequestDTO.class);

        validateUserInput(
                periodoPontoDTO.getEntradaPeriodo(),
                periodoPontoDTO.getSaidaPeriodo()
        );

        PeriodoPonto periodoPonto = periodoPontoUtils.findById(periodoPontoDTO.getId());

        periodoPonto.setEntradaPeriodo(periodoPontoDTO.getEntradaPeriodo());
        periodoPonto.setSaidaPeriodo(periodoPontoDTO.getSaidaPeriodo());

        out.print(jsonUtils.buildJsonResponse(new ServletMessageResponse("Periodo de ponto atualizado com sucesso!")));
    }

    @Override
    protected void doOptions(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void validatePeriodoPontoLimit() {
        int PERIODO_PONTO_MAX_LIMIT = 3;

        if (PeriodoPontoData.getPeriodoPonto().size() >= PERIODO_PONTO_MAX_LIMIT) {
            throw new IllegalArgumentException("Limite de periodos atingido!");
        }
    }

    private void validateUserInput(LocalTime entrada,
                                   LocalTime saida) {
        if (Objects.isNull(entrada) || Objects.isNull(saida)) {
            throw new IllegalArgumentException("O período inicial e final da manhã e tarde são obrigatórios");
        }
    }

    private void deleteById(UUID id) {
        PeriodoPontoData
                .getPeriodoPonto()
                .removeIf(periodo ->
                        periodo.getId().equals(id)
                );
    }
}