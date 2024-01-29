package com.insight.pontoapp.servlets;

import com.insight.pontoapp.config.ObjectMapperConfig;
import com.insight.pontoapp.data.MarcacoesData;
import com.insight.pontoapp.data.PeriodoPontoData;
import com.insight.pontoapp.domain.DTO.MarcacaoRequestDTO;
import com.insight.pontoapp.domain.DTO.MarcacaoRequestUpdateDTO;
import com.insight.pontoapp.domain.DTO.MarcacaoResponseDTO;
import com.insight.pontoapp.domain.DTO.ServletMessageResponse;
import com.insight.pontoapp.domain.models.Marcacao;
import com.insight.pontoapp.utils.json.JsonUtils;
import com.insight.pontoapp.utils.json.JsonUtilsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/marcacoes")
public class MarcacoesServlet extends HttpServlet {
    private static final long serialVersionUID = 7302329288889146232L;
    private final JsonUtils jsonUtils = new JsonUtilsImpl();
    private final ObjectMapperConfig mapperConfig = new ObjectMapperConfig();

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");

        if (!hasPeriodoPonto()) {
            throw new IllegalArgumentException("É necessário cadastrar o período do ponto antes de realizar uma marcação");
        }

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        MarcacaoRequestDTO marcacaoJson = mapperConfig.objectMapper().readValue(requestBody, MarcacaoRequestDTO.class);

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

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_CREATED);
        PrintWriter out = response.getWriter();
        out.print(jsonUtils.buildJsonResponse(new ServletMessageResponse("Marcação registrada com sucesso!")));
    }

    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        MarcacaoRequestUpdateDTO marcacaoJson = mapperConfig.objectMapper().readValue(requestBody, MarcacaoRequestUpdateDTO.class);

        Marcacao marcacaoById = findById(marcacaoJson.getId());

        marcacaoById.setEntradaManha(marcacaoJson.getEntradaManha());
        marcacaoById.setSaidaManha(marcacaoJson.getSaidaManha());
        marcacaoById.setEntradaTarde(marcacaoJson.getEntradaTarde());
        marcacaoById.setSaidaTarde(marcacaoJson.getSaidaTarde());

        out.print(jsonUtils.buildJsonResponse(new ServletMessageResponse("Marcacao editada com sucesso")));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();

        String marcacaoId = request.getParameter("id");

        if (marcacaoId != null && !marcacaoId.isEmpty()) {
            Marcacao marcacao = findById(UUID.fromString(marcacaoId));
            out.print(jsonUtils.buildJsonResponse(buildMarcacaoDTO(marcacao)));
            return;
        }

        out.print(jsonUtils.buildJsonResponse(buildMarcacaoResponseDTOList()));
    }

    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.addHeader("Access-Control-Allow-Origin", "*");

        String marcacaoId = request.getParameter("id");
        UUID marcacaoUUID = UUID.fromString(marcacaoId);

        deleteById(marcacaoUUID);

        response.setContentType("application/json");
        out.print(jsonUtils.buildJsonResponse(new ServletMessageResponse("Marcacao apagada com sucesso!")));
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        response.setStatus(HttpServletResponse.SC_OK);
    }

    private List<MarcacaoResponseDTO> buildMarcacaoResponseDTOList() {
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
                                        marcacao.getSaidaTarde()
                        )
                ));

        return marcacaoResponseDTO;
    }

    private MarcacaoResponseDTO buildMarcacaoDTO(Marcacao marcacao) {
        return new MarcacaoResponseDTO(
                marcacao.getId(),
                marcacao.getEntradaManha(),
                marcacao.getSaidaManha(),
                marcacao.getEntradaTarde(),
                marcacao.getSaidaTarde()
        );
    }

    private Marcacao findById(UUID id) {
        return MarcacoesData
                .getMarcacoesData()
                .stream()
                .filter(marcacao -> marcacao.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Marcacao nao encotrada com o ID informado"));
    }

    private boolean hasPeriodoPonto() {
        return !PeriodoPontoData.getPeriodoPonto().isEmpty();
    }

    private void deleteById(UUID id) {
        MarcacoesData
                .getMarcacoesData()
                .removeIf(marcacao ->
                        marcacao.getId().equals(id)
                );
    }
}
