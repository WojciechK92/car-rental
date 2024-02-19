package com.github.wojciechk92.carrental.employee.validator;

public interface EmployeeValidator {

  boolean validateUniqueEmail(String email);

  boolean validateUniqueTel(int tel);
}
