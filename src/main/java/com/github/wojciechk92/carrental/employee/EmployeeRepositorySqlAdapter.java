package com.github.wojciechk92.carrental.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositorySqlAdapter extends EmployeeRepository, JpaRepository<Employee, Long> {

  @NonNull
  @Override
  @Query("select e from Employee e join fetch e.rentals")
  Page<Employee> findAll(@NonNull Pageable pageable);

  @Override
  boolean existsByPersonalDetailsEmail(String email);

  @Override
  boolean existsByPersonalDetailsTel(int tel);

}
