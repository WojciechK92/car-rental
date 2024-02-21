package com.github.wojciechk92.carrental.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
interface ClientRepositorySqlAdapter extends ClientRepository, JpaRepository<Client, Long> {

  @NonNull
  @Override
  @Query("select c from Client c join fetch c.rentals")
  Page<Client> findAll(@NonNull Pageable pageable);

  @Override
  boolean existsByPersonalDetailsEmail(String email);

  @Override
  boolean existsByPersonalDetailsTel(int tel);

}
