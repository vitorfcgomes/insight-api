package management.insight_api.dto.response;

import java.time.LocalDate;

public record ClientResponse(Long id, String name, String email, String company, String phone, LocalDate contractDate) {
}
