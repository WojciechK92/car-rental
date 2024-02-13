package com.github.wojciechk92.carrental.rental;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

interface RentalRepository {

  Page<Rental> findAll(Pageable pageable);

  Optional<Rental> findById(Long id);

  Rental save(Rental rental);

}
