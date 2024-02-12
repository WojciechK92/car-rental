package com.github.wojciechk92.carrental.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CarRepository {

  Page<Car> findAll(Pageable pageable);

  Optional<Car> findById(Long id);

  Car save(Car car);

}
