package com.github.wojciechk92.carrental.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RentalRepositorySqlAdapter extends RentalRepository, JpaRepository<Rental, Long> {
}
