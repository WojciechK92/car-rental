package com.github.wojciechk92.carrental.client;

import com.github.wojciechk92.carrental.client.dto.ClientReadModel;
import com.github.wojciechk92.carrental.client.dto.ClientWriteModel;
import com.github.wojciechk92.carrental.client.exception.ClientException;
import com.github.wojciechk92.carrental.client.exception.ClientExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ClientServiceImpl implements ClientService {
  private final ClientRepository clientRepository;

  @Autowired
  ClientServiceImpl(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public List<ClientReadModel> getClients(Pageable pageable) {
    return clientRepository.findAll(pageable).getContent().stream()
            .map(ClientReadModel::new)
            .toList();
  }

  @Override
  public ClientReadModel getClient(Long id) {
    return clientRepository.findById(id)
            .map(ClientReadModel::new)
            .orElseThrow(() -> new ClientException(ClientExceptionMessage.CLIENT_NOT_FOUND));
  }

  @Override
  public ClientReadModel createClient(ClientWriteModel toSave) {
    Client result = clientRepository.save(toSave.toClient());
    return new ClientReadModel(result);
  }
}
