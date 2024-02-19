package com.github.wojciechk92.carrental.client;

import com.github.wojciechk92.carrental.client.dto.ClientReadModel;
import com.github.wojciechk92.carrental.client.dto.ClientWriteModel;
import com.github.wojciechk92.carrental.client.exception.ClientException;
import com.github.wojciechk92.carrental.client.exception.ClientExceptionMessage;
import com.github.wojciechk92.carrental.client.validator.ClientValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ClientServiceImpl implements ClientService {
  private final ClientRepository clientRepository;
  private final ClientValidator validator;

  @Autowired
  ClientServiceImpl(ClientRepository clientRepository, ClientValidator validator) {
    this.clientRepository = clientRepository;
    this.validator = validator;
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
    boolean uniqueEmail = validator.validateUniqueEmail(toSave.getEmail());
    boolean uniqueTel = validator.validateUniqueTel(toSave.getTel());

    if (!uniqueEmail) throw new ClientException(ClientExceptionMessage.EMAIL_IS_NOT_UNIQUE);
    if (!uniqueTel) throw new ClientException(ClientExceptionMessage.TEL_IS_NOT_UNIQUE);

    Client result = clientRepository.save(toSave.toClient());
    return new ClientReadModel(result);
  }

  @Transactional
  @Override
  public void updateClient(ClientWriteModel toUpdate, Long id) {
    boolean uniqueEmail = validator.validateUniqueEmail(toUpdate.getEmail());
    boolean uniqueTel = validator.validateUniqueTel(toUpdate.getTel());

    clientRepository.findById(id)
            .map(client -> {
              if (!uniqueEmail && !client.getPersonalDetails().getEmail().equals(toUpdate.getEmail())) throw new ClientException(ClientExceptionMessage.EMAIL_IS_NOT_UNIQUE);
              if (!uniqueTel && client.getPersonalDetails().getTel() != toUpdate.getTel()) throw new ClientException(ClientExceptionMessage.TEL_IS_NOT_UNIQUE);
              client.getPersonalDetails().setFirstName(toUpdate.getFirstName());
              client.getPersonalDetails().setLastName(toUpdate.getLastName());
              client.getPersonalDetails().setEmail(toUpdate.getEmail());
              client.getPersonalDetails().setTel(toUpdate.getTel());
              client.setStatus(toUpdate.getStatus());
              return client;
            })
            .orElseThrow(() -> new ClientException(ClientExceptionMessage.CLIENT_NOT_FOUND));
  }

  @Transactional
  @Override
  public void setStatusTo(ClientStatus status, Long id) {
    clientRepository.findById(id)
            .map(client -> {
              client.setStatus(status);
              return client;
            })
            .orElseThrow(() -> new ClientException(ClientExceptionMessage.CLIENT_NOT_FOUND));
  }
}
