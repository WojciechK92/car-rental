package com.github.wojciechk92.carrental.rental.validator;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.employee.Employee;

import java.util.List;
import java.util.Set;

public interface RentalValidator {

    Employee checkEmployeeById(Long id);

    Client checkClientById(Long id);

    Set<Car> checkCarsByIdList(List<Long> id);

  }
