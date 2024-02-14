package com.github.wojciechk92.carrental.rental.validator;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.car.CarService;
import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.client.ClientService;
import com.github.wojciechk92.carrental.client.dto.ClientReadModel;
import com.github.wojciechk92.carrental.employee.Employee;
import com.github.wojciechk92.carrental.employee.EmployeeService;
import com.github.wojciechk92.carrental.employee.dto.EmployeeReadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class RentalValidatorImpl implements RentalValidator {
  private final EmployeeService employeeService;
  private final ClientService clientService;
  private final CarService carService;

  @Autowired
  public RentalValidatorImpl(EmployeeService employeeService, ClientService clientService, CarService carService) {
    this.employeeService = employeeService;
    this.clientService = clientService;
    this.carService = carService;
  }

  @Override
  public Employee checkEmployeeById(Long id) {
    EmployeeReadModel result = employeeService.getEmployee(id);
    return new Employee(result);
  }

  @Override
  public Client checkClientById(Long id) {
    ClientReadModel result = clientService.getClient(id);
    return new Client(result);
  }

  @Override
  public Set<Car> checkCarsByIdList(List<Long> list) {
    List<CarReadModel> result = carService.getCarsByIdList(list);
    return result.stream()
            .map(Car::new)
            .collect(Collectors.toSet());
  }
}
