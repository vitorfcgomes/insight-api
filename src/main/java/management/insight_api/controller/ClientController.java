package management.insight_api.controller;


import lombok.RequiredArgsConstructor;
import management.insight_api.dto.request.ClientRequest;
import management.insight_api.dto.response.ClientResponse;
import management.insight_api.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody ClientRequest clientRequest) {
        ClientResponse response = clientService.create(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(clientService.update(id, clientRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


