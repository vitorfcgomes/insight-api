package management.insight_api.repository;

import management.insight_api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;


public interface DashboardRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p.status, COUNT(p) FROM Project p GROUP BY p.status")
    List<Object[]> countByStatus();

    @Query("SELECT p FROM Project p WHERE p.expectedEndDate < CURRENT_DATE AND p.status = management.insight_api.model.enums.ProjectStatus.DELAYED")
    List<Project> findDelayedProjects();

    @Query("SELECT p.client.name, COUNT(p) FROM Project p GROUP BY p.client.name ORDER BY COUNT(p) DESC")
    List<Object[]> topClientsByProjects();

    @Query("SELECT SUM(p.value) FROM Project p")
    BigDecimal totalRevenue();
}
