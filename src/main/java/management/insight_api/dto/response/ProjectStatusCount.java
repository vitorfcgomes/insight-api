package management.insight_api.dto.response;

import management.insight_api.model.enums.ProjectStatus;

public record ProjectStatusCount(ProjectStatus status, Long count) {
}
