package management.insight_api.dto.request;

import java.time.LocalDate;

public record ClientRequest(String name, String email, String company, String phone, LocalDate contractDate) {
}
