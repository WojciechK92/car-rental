package com.github.wojciechk92.carrental.client.validator;

import com.github.wojciechk92.carrental.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientValidatorImpl implements ClientValidator {
  private final ClientRepository clientRepository;

  @Autowired
  public ClientValidatorImpl(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public boolean validateUniqueEmail(String email) {
    return !clientRepository.existsByPersonalDetailsEmail(email);
  }

  @Override
  public boolean validateUniqueTel(int tel) {
    return !clientRepository.existsByPersonalDetailsTel(tel);
  }
}
