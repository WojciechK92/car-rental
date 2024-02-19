package com.github.wojciechk92.carrental.employee.validator;

import com.github.wojciechk92.carrental.employee.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidatorImpl implements EmployeeValidator {
  private final EmployeeRepository employeeRepository;

  public EmployeeValidatorImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public boolean validateUniqueEmail(String email) {
    return !employeeRepository.existsByPersonalDetailsEmail(email);
  }

  @Override
  public boolean validateUniqueTel(int tel) {
    return !employeeRepository.existsByPersonalDetailsTel(tel);
  }
}
