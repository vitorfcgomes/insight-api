package management.insight_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import management.insight_api.model.enums.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_projects")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate endDate;
    private BigDecimal value;

}
