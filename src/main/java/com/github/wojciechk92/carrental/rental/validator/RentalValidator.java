package com.github.wojciechk92.carrental.rental.validator;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.employee.Employee;
import com.github.wojciechk92.carrental.rental.Rental;
import com.github.wojciechk92.carrental.rental.RentalStatus;
import com.github.wojciechk92.carrental.rental.dto.RentalWriteModel;

import java.util.List;
import java.util.Set;

public interface RentalValidator {

  RentalStatus checkIfStatusIsCompletedOrCanceled(Rental previousRental, RentalWriteModel nextRental);

  void checkIfCarsAreAvailable(Rental previousRental, List<CarReadModel> nextCars);

  Employee checkEmployeeById(Long id);

  Client checkClientById(Long id);

  Set<Car> checkCarsByIdList(Rental previousRental, RentalWriteModel nextRental);
}
