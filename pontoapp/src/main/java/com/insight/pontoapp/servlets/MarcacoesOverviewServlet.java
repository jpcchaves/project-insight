package com.insight.pontoapp.servlets;

import com.insight.pontoapp.data.MarcacoesData;
import com.insight.pontoapp.domain.DTO.MarcacoesOverviewDTO;
import com.insight.pontoapp.domain.models.Marcacao;
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
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@WebServlet("/marcacoes/overview")
public class MarcacoesOverviewServlet extends HttpServlet {

    private static final long serialVersionUID = 1408002325311870041L;

    private final JsonUtils jsonUtils = new JsonUtilsImpl();
    private final PeriodoPontoUtils periodoPontoUtils = new PeriodoPontoUtilsImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        String periodoPontoId = request.getParameter("id");
        PeriodoPonto periodoPontoById = periodoPontoUtils.findById(UUID.fromString(periodoPontoId));

        PrintWriter out = response.getWriter();
        out.print(jsonUtils.buildJsonResponse(calcularAtrasoEHoraExtra(periodoPontoById, new MarcacoesOverviewDTO())));
    }

    public MarcacoesOverviewDTO calcularAtrasoEHoraExtra(PeriodoPonto periodoPonto, MarcacoesOverviewDTO overviewDTO) {
        List<Marcacao> marcacaoList = MarcacoesData.getMarcacoesData();

        Duration totalAtraso = Duration.ZERO;
        Duration totalHoraExtra = Duration.ZERO;

        Duration cargaHorariaDiaria = periodoPonto.calcularCargaHoraria();

        for (Marcacao marcacao : marcacaoList) {
            LocalTime entradaManha = marcacao.getEntradaManha();
            LocalTime saidaManha = marcacao.getSaidaManha();
            LocalTime entradaTarde = marcacao.getEntradaTarde();
            LocalTime saidaTarde = marcacao.getSaidaTarde();

            Duration totalTrabalhado = Duration.ZERO;

            Duration totalManha = calcularDiferenca(entradaManha, saidaManha);
            totalTrabalhado = totalTrabalhado.plus(totalManha);

            Duration totalTarde = calcularDiferenca(entradaTarde, saidaTarde);
            totalTrabalhado = totalTrabalhado.plus(totalTarde);

            if (totalTrabalhado.compareTo(cargaHorariaDiaria) > 0) {
                totalHoraExtra = totalHoraExtra.plus(totalTrabalhado.minus(cargaHorariaDiaria));
            } else {
                totalAtraso = totalAtraso.plus(cargaHorariaDiaria.minus(totalTrabalhado));
            }
        }

        overviewDTO.setAtraso(formatDuration(totalAtraso));
        overviewDTO.setHoraExtra(formatDuration(totalHoraExtra));

        Duration saldo = totalHoraExtra.minus(totalAtraso);
        overviewDTO.setSaldo(formatSaldo(saldo));

        return overviewDTO;
    }

    private Duration calcularDiferenca(LocalTime inicio, LocalTime fim) {
        return Duration.between(inicio, fim);
    }

    private static String formatDuration(Duration duration) {
        long totalHours = duration.toHours();
        long minutes = (duration.toMinutes() - totalHours * 60);

        return String.format("%02d:%02d", Math.abs(totalHours), Math.abs(minutes));
    }

    private static String formatSaldo(Duration duracao) {
        long totalHoras = duracao.toHours();
        long minutos = (duracao.toMinutes() - totalHoras * 60);

        String sinal = duracao.isNegative() ? "-" : (duracao.isZero() ? "" : "+");

        return String.format("%s%02d:%02d", sinal, Math.abs(totalHoras), Math.abs(minutos));
    }
}
