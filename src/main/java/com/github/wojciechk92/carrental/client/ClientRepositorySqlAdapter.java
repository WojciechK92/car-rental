package com.github.wojciechk92.carrental.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ClientRepositorySqlAdapter extends ClientRepository, JpaRepository<Client, Long> {

  @Override
  boolean existsByEmail(String email);

  @Override
  boolean existsByTel(int tel);

}
