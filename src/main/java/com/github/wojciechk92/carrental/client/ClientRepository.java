package com.github.wojciechk92.carrental.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClientRepository {

  Page<Client> findAll(Pageable pageable);

  Optional<Client> findById(Long id);

  Client save(Client client);

}
