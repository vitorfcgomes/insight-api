package management.insight_api.controller;

import lombok.RequiredArgsConstructor;
import management.insight_api.dto.response.InsightResponse;
import management.insight_api.service.InsightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insight")
@RequiredArgsConstructor
public class InsightController {
    private final InsightService insightService;

    @GetMapping("/executive")
    public ResponseEntity<InsightResponse> generateExecutiveInsight(){
        return ResponseEntity.ok(insightService.generateExecutiveInsight());
    }
}
