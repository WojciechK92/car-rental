package com.github.wojciechk92.carrental.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CarRepositorySqlAdapter extends CarRepository, JpaRepository<Car, Long> {

  @NonNull
  @Override
  @Query("select distinct c from Car c join fetch c.rentals")
  Page<Car> findAll(@NonNull Pageable pageable);


  @Override
  List<Car> findAllByIdIsIn(List<Long> list);

  @Override
  Page<Car> findAllByStatus(CarStatus status, Pageable pageable);
}
