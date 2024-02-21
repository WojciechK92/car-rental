package com.github.wojciechk92.carrental.rental.validator;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.car.CarService;
import com.github.wojciechk92.carrental.car.CarStatus;
import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.client.ClientService;
import com.github.wojciechk92.carrental.client.ClientStatus;
import com.github.wojciechk92.carrental.client.dto.ClientReadModel;
import com.github.wojciechk92.carrental.employee.Employee;
import com.github.wojciechk92.carrental.employee.EmployeeService;
import com.github.wojciechk92.carrental.employee.EmployeeStatus;
import com.github.wojciechk92.carrental.employee.dto.EmployeeReadModel;
import com.github.wojciechk92.carrental.rental.Rental;
import com.github.wojciechk92.carrental.rental.RentalStatus;
import com.github.wojciechk92.carrental.rental.dto.RentalWriteModel;
import com.github.wojciechk92.carrental.rental.exception.RentalException;
import com.github.wojciechk92.carrental.rental.exception.RentalExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
  public RentalStatus checkIfStatusIsCompletedOrCanceled(Rental previousRental, RentalWriteModel nextRental) {
    if (RentalStatus.COMPLETED.equals(previousRental.getStatus())) throw new RentalException(RentalExceptionMessage.RENTAL_STATUS_IS_ALREADY_COMPLETED);
    if (RentalStatus.CANCELLED.equals(previousRental.getStatus())) throw new RentalException(RentalExceptionMessage.RENTAL_STATUS_IS_ALREADY_CANCELED);

    if (nextRental != null) return nextRental.getStatus();
    return null;
  }

  @Override
  public Employee checkEmployeeById(Long id) {
    EmployeeReadModel result = employeeService.getEmployee(id);
    if (EmployeeStatus.EMPLOYED != result.getStatus()) {
      throw new RentalException(RentalExceptionMessage.EMPLOYEE_STATUS_IS_NOT_EMPLOYED);
    }
    return new Employee(result);
  }

  @Override
  public Client checkClientById(Long id) {
    ClientReadModel result = clientService.getClient(id);
    if (ClientStatus.ACTIVE != result.getStatus()) {
      throw new RentalException(RentalExceptionMessage.CLIENT_STATUS_IS_NOT_ACTIVE);
    }
    return new Client(result);
  }

  @Override
  public Set<Car> checkCarsByIdList(Rental previousRental, RentalWriteModel nextRental) {
    List<CarReadModel> nextCars = carService.getCarsByIdList(nextRental.getCarsIdList());

    if (nextCars.isEmpty()) throw new RentalException(RentalExceptionMessage.CAR_LIST_IS_EMPTY);
    checkIfCarsAreAvailable(previousRental, nextCars);

    return nextCars.stream()
            .map(Car::new)
            .collect(Collectors.toSet());
  }

  public void checkIfCarsAreAvailable(Rental previousRental, List<CarReadModel> nextCars) {
    List<Long> previousCars = previousRental == null
            ? Collections.emptyList()
            : previousRental.getCars().stream().map(Car::getId).toList();

    boolean statusIsNotAvailable = nextCars.stream()
              .anyMatch(car -> CarStatus.AVAILABLE != car.getStatus() && !previousCars.contains(car.getId()));

    if (statusIsNotAvailable) throw new RentalException(RentalExceptionMessage.CAR_STATUS_IS_NOT_AVAILABLE);
  }
}
