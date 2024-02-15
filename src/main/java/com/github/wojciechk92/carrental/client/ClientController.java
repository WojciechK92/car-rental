package com.github.wojciechk92.carrental.client;

import com.github.wojciechk92.carrental.client.dto.ClientReadModel;
import com.github.wojciechk92.carrental.client.dto.ClientWriteModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {
  private final ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  ResponseEntity<List<ClientReadModel>> getClients(Pageable pageable) {
    return ResponseEntity.ok(clientService.getClients(pageable));
  }

  @GetMapping("/{id}")
  ResponseEntity<ClientReadModel> getClient(@PathVariable Long id) {
    return ResponseEntity.ok(clientService.getClient(id));
  }

  @PostMapping
  ResponseEntity<ClientReadModel> createClient(@RequestBody @Valid ClientWriteModel client) {
    ClientReadModel result = clientService.createClient(client);
    return ResponseEntity.created(URI.create("/clients/" + result.getId())).body(result);
  }

  @PutMapping("/{id}")
  ResponseEntity<Void> updateClient(@RequestBody @Valid ClientWriteModel client, @PathVariable Long id) {
    clientService.updateClient(client, id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Void> setStatusTo(@RequestParam ClientStatus status, @PathVariable Long id) {
    clientService.setStatusTo(status, id);
    return ResponseEntity.noContent().build();
  }


}
