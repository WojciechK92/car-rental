package com.github.wojciechk92.carrental.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmployeeRepository {

  Page<Employee> findAll(Pageable pageable);

  Optional<Employee> findById(Long id);

  Employee save(Employee employee);

  boolean existsByPersonalDetailsEmail(String email);

  boolean existsByPersonalDetailsTel(int tel);

}
