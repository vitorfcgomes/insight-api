package management.insight_api.dto.request;

import management.insight_api.model.enums.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectRequest(String title, String description,
                             Long clientId, ProjectStatus status,
                             LocalDate startDate, LocalDate expectedEndDate, BigDecimal value) {
}
