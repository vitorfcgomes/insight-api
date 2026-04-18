package management.insight_api.service;

import lombok.RequiredArgsConstructor;
import management.insight_api.dto.request.GroqRequest;
import management.insight_api.dto.response.GroqResponse;
import management.insight_api.dto.response.InsightResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsightService {
    private final DashboardService dashboardService;
    private final RestClient restClient = RestClient.create();

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    public InsightResponse generateExecutiveInsight() {
        var statusList = dashboardService.countByStatus();
        var delayedProjects = dashboardService.findDelayedProject();
        var topClients = dashboardService.topClientsByProjects();
        var revenue = dashboardService.totalRevenue();

        String prompt = """
        Você é um analista de dados de uma empresa especializada em BI e Inteligência Artificial.
        Analise os dados abaixo e gere um resumo executivo profissional em português:
        
        Projetos por status:
        %s
        
        Quantidade de projetos atrasados: %d
        
        Top clientes por número de projetos:
        %s
        
        Receita total: R$ %s
        
        Seja objetivo, profissional e aponte pontos de atenção se houver.
        """.formatted(
                statusList.stream()
                        .map(s -> s.status() + ": " + s.count() + " projeto(s)")
                        .reduce("", (a, b) -> a + "\n" + b),
                delayedProjects.size(),
                topClients.stream()
                        .map(c -> c.clientName() + ": " + c.projectCount() + " projeto(s)")
                        .reduce("", (a, b) -> a + "\n" + b),
                revenue.totalRevenue()
        );

        var request = new GroqRequest(
                "llama-3.3-70b-versatile",
                List.of(new GroqRequest.Message("user", prompt))
        );

        GroqResponse response = restClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(GroqResponse.class);

        String insight = response.choices().get(0).message().content();
        return new InsightResponse(insight);
    }
}
