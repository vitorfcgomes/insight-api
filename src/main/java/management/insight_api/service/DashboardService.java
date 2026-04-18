package management.insight_api.service;

import lombok.RequiredArgsConstructor;
import management.insight_api.dto.response.ClientProjectCount;
import management.insight_api.dto.response.ProjectStatusCount;
import management.insight_api.dto.response.RevenueResponse;
import management.insight_api.model.Project;
import management.insight_api.model.enums.ProjectStatus;
import management.insight_api.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DashboardRepository dashboardRepository;

    public List<ProjectStatusCount> countByStatus() {
        return dashboardRepository.countByStatus()
                .stream()
                .map(row -> new ProjectStatusCount(
                        (ProjectStatus) row[0],
                        (Long) row[1]
                ))
                .toList();
    }

    public List<Project> findDelayedProject(){
        return dashboardRepository.findDelayedProjects();
    }

    public List<ClientProjectCount> topClientsByProjects(){
        return dashboardRepository.topClientsByProjects()
                .stream()
                .map(row -> new ClientProjectCount(
                        (String) row [0],
                        (Long) row [1]))
                        .toList();
    }

    public RevenueResponse totalRevenue(){
        return new RevenueResponse(dashboardRepository.totalRevenue());
    }
}
