package com.github.wojciechk92.carrental.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CarRepositorySqlAdapter extends CarRepository, JpaRepository<Car, Long> {

  @Override
  List<Car> findAllByIdIsIn(List<Long> list);
}
