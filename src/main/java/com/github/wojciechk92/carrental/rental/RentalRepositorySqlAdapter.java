package com.github.wojciechk92.carrental.rental;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
interface RentalRepositorySqlAdapter extends RentalRepository, JpaRepository<Rental, Long> {

  @NonNull
  @Override
  @Query("select r from Rental r join fetch r.cars join fetch r.client join fetch r.employee")
  Page<Rental> findAll(@NonNull Pageable pageable);
}
