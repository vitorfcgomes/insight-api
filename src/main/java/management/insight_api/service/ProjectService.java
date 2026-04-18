package management.insight_api.service;

import lombok.RequiredArgsConstructor;
import management.insight_api.dto.request.ProjectRequest;
import management.insight_api.dto.response.ClientResponse;
import management.insight_api.dto.response.ProjectResponse;
import management.insight_api.model.Client;
import management.insight_api.model.Project;
import management.insight_api.repository.ClientRepository;
import management.insight_api.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;

    public ProjectResponse create(ProjectRequest request) {
        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        Project project = new Project();
        project.setTitle(request.title());
        project.setDescription(request.description());
        project.setClient(client);
        project.setStatus(request.status());
        project.setStartDate(request.startDate());
        project.setExpectedEndDate(request.expectedEndDate());
        project.setEndDate(null);
        project.setValue(request.value());

        Project saved = projectRepository.save(project);
        return toProjectResponse(saved);
    }

    public List<ProjectResponse> findAll() {
        return projectRepository.findAll()
                .stream()
                .map(this::toProjectResponse)
                .toList();
    }

    public ProjectResponse findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));
        return toProjectResponse(project);
    }

    public ProjectResponse update(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));

        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        project.setTitle(request.title());
        project.setDescription(request.description());
        project.setClient(client);
        project.setStatus(request.status());
        project.setStartDate(request.startDate());
        project.setExpectedEndDate(request.expectedEndDate());
        project.setValue(request.value());

        Project saved = projectRepository.save(project);
        return toProjectResponse(saved);
    }

    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado!");
        }
        projectRepository.deleteById(id);
    }

    private ProjectResponse toProjectResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                toClientResponse(project.getClient()),
                project.getStatus(),
                project.getStartDate(),
                project.getExpectedEndDate(),
                project.getEndDate(),
                project.getValue()
        );
    }

    private ClientResponse toClientResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getCompany(),
                client.getPhone(),
                client.getContractDate()
        );
    }
}