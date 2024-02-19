package com.github.wojciechk92.carrental.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositorySqlAdapter extends EmployeeRepository, JpaRepository<Employee, Long> {

  @Override
  boolean existsByEmail(String email);

  @Override
  boolean existsByTel(int tel);

}
