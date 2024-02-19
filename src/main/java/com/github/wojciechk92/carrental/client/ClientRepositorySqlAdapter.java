package com.github.wojciechk92.carrental.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ClientRepositorySqlAdapter extends ClientRepository, JpaRepository<Client, Long> {

  @Override
  boolean existsByPersonalDetailsEmail(String email);

  @Override
  boolean existsByPersonalDetailsTel(int tel);

}
