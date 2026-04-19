package management.insight_api.controller;

import lombok.RequiredArgsConstructor;
import management.insight_api.dto.response.ClientProjectCount;
import management.insight_api.dto.response.ProjectStatusCount;
import management.insight_api.dto.response.RevenueResponse;
import management.insight_api.model.Project;
import management.insight_api.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboard;

    @GetMapping("/projects/status")
    public ResponseEntity<List<ProjectStatusCount>> countByStatus(){
        return ResponseEntity.ok(dashboard.countByStatus());
    }

    @GetMapping("/projects/delayed")
    public ResponseEntity<List<Project>> findDelayedProject(){
        return ResponseEntity.ok(dashboard.findDelayedProject());
    }

    @GetMapping("/clients/top")
    public ResponseEntity<List<ClientProjectCount>> topClientsByProjects(){
        return ResponseEntity.ok(dashboard.topClientsByProjects());
    }

    @GetMapping("/revenue")
    public ResponseEntity<RevenueResponse> totalRevenue(){
        return ResponseEntity.ok(dashboard.totalRevenue());
    }
}
