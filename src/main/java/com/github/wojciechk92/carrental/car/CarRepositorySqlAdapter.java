package com.github.wojciechk92.carrental.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepositorySqlAdapter extends CarRepository, JpaRepository<Car, Long> {
}
