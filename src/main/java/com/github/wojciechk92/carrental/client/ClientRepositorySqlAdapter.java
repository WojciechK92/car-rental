package com.github.wojciechk92.carrental.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepositorySqlAdapter extends ClientRepository, JpaRepository<Client, Long> {

}
