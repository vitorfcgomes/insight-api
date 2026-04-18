package management.insight_api.exception;

import java.time.LocalDateTime;

public record ErrorResponse(String message, String status, LocalDateTime timestamp) {
}
