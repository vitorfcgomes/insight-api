package management.insight_api.dto.response;

import management.insight_api.model.enums.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDate;


public record ProjectResponse(Long id, String title, String description,
                              ClientResponse clientId, ProjectStatus status, LocalDate startDate,
                              LocalDate expectedEndDate, LocalDate endDate, BigDecimal value) {
}
