package com.github.wojciechk92.carrental.client;

import com.github.wojciechk92.carrental.client.dto.ClientReadModel;
import com.github.wojciechk92.carrental.client.dto.ClientWriteModel;
import com.github.wojciechk92.carrental.client.exception.ClientException;
import com.github.wojciechk92.carrental.client.exception.ClientExceptionMessage;
import com.github.wojciechk92.carrental.common.personalDetails.PersonalDetails;
import com.github.wojciechk92.carrental.common.personalDetails.PersonalDetailsSetter;
import com.github.wojciechk92.carrental.common.personalDetails.PersonalDetailsValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ClientServiceImpl implements ClientService {
  private final ClientRepository clientRepository;
  private final PersonalDetailsValidator personalDetailsValidator;
  private final PersonalDetailsSetter personalDetailsSetter;

  @Autowired
  ClientServiceImpl(ClientRepository clientRepository, PersonalDetailsValidator personalDetailsValidator, PersonalDetailsSetter personalDetailsSetter) {
    this.clientRepository = clientRepository;
    this.personalDetailsValidator = personalDetailsValidator;
    this.personalDetailsSetter = personalDetailsSetter;
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
    personalDetailsValidator.validateOnSaveForClient(toSave);

    Client result = clientRepository.save(toSave.toClient());
    return new ClientReadModel(result);
  }

  @Transactional
  @Override
  public void updateClient(ClientWriteModel toUpdate, Long id) {

    clientRepository.findById(id)
            .map(client -> {
              personalDetailsValidator.validateOnUpdateForClient(client, toUpdate);
              PersonalDetails personalDetails = personalDetailsSetter.setPersonalDetailsForClient(client, toUpdate);
              client.setPersonalDetails(personalDetails);
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
