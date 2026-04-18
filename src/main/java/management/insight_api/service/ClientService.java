package management.insight_api.service;

import lombok.RequiredArgsConstructor;
import management.insight_api.dto.request.ClientRequest;
import management.insight_api.dto.response.ClientResponse;
import management.insight_api.model.Client;
import management.insight_api.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientResponse create(ClientRequest request){
        Client client = new Client();

        client.setName(request.name());
        client.setCompany(request.company());
        client.setPhone(request.phone());
        client.setContractDate(request.contractDate());
        client.setEmail(request.email());

        Client saved = clientRepository.save(client);

        return new ClientResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getCompany(), saved.getPhone(), saved.getContractDate());
    }

    public List<ClientResponse> findAll(){
     return clientRepository.findAll()
             .stream()
             .map(client -> new ClientResponse
                     (client.getId(), client.getName(), client.getEmail(),
                             client.getCompany(), client.getPhone(), client.getContractDate()))
             .toList();
    }

    public ClientResponse findById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado! "));
        return new ClientResponse(client.getId(), client.getName(), client.getEmail(), client.getCompany(), client.getPhone(), client.getContractDate());
    }

    public ClientResponse update(Long id, ClientRequest request){
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado! "));

        client.setName(request.name());
        client.setCompany(request.company());
        client.setPhone(request.phone());
        client.setContractDate(request.contractDate());
        client.setEmail(request.email());

        Client saved = clientRepository.save(client);

        return new ClientResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getCompany(), saved.getPhone(), saved.getContractDate());
    }

    public void delete(Long id){
        if(!clientRepository.existsById(id)){
            throw new RuntimeException("Cliente não encontrado!");
        }
        clientRepository.deleteById(id);
    }
}
