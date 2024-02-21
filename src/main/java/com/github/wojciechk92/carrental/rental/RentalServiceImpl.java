package com.github.wojciechk92.carrental.rental;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.car.CarService;
import com.github.wojciechk92.carrental.car.CarStatus;
import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.employee.Employee;
import com.github.wojciechk92.carrental.rental.dto.RentalReadModel;
import com.github.wojciechk92.carrental.rental.dto.RentalWriteModel;
import com.github.wojciechk92.carrental.rental.exception.RentalException;
import com.github.wojciechk92.carrental.rental.exception.RentalExceptionMessage;
import com.github.wojciechk92.carrental.rental.validator.RentalValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
class RentalServiceImpl implements RentalService {
  private final RentalRepository rentalRepository;
  private final RentalValidator validator;
  private final CarService carService;

  @Autowired
  RentalServiceImpl(RentalRepository rentalRepository, RentalValidator validator, CarService carService) {
    this.rentalRepository = rentalRepository;
    this.validator = validator;
    this.carService = carService;
  }

  @Override
  public List<RentalReadModel> getRentals(Pageable pageable) {
    return rentalRepository.findAll(pageable).getContent().stream()
            .map(RentalReadModel::new)
            .toList();
  }

  @Override
  public RentalReadModel getRental(Long id) {
    return rentalRepository.findById(id)
            .map(RentalReadModel::new)
            .orElseThrow(() -> new RentalException(RentalExceptionMessage.RENTAL_NOT_FOUND));
  }

  @Override
  public RentalReadModel createRental(RentalWriteModel toSave) {
    Rental rental = validateRentalOnSave(toSave);
    Rental result = rentalRepository.save(rental);

    List<Long> list = result.getCars().stream()
            .map(Car::getId)
            .toList();
    carService.setStatusForCarsFromIdList(list, CarStatus.RENTAL);

    return new RentalReadModel(result);
  }

  @Transactional
  @Override
  public void updateRental(RentalWriteModel toUpdate, Long id) {
    rentalRepository.findById(id)
            .map(rentalFromDb -> {
              List<Long> previousCars = rentalFromDb.getCars().stream().map(Car::getId).toList();
              List<Long> nextCars = toUpdate.getCarsIdList();
              Rental rental = validateRentalOnUpdate(rentalFromDb, toUpdate);
              updateStatusForCarsFromList(previousCars, nextCars);
              return rental;
            })
            .orElseThrow(() -> new RentalException(RentalExceptionMessage.RENTAL_NOT_FOUND));
  }

  @Override
  public void closeRental(Long id) {
    rentalRepository.findById(id)
            .map(rental -> {
              validator.checkIfStatusIsCompletedOrCanceled(rental, null);
              rental.setStatus(RentalStatus.COMPLETED);
              rental.setReturnDate(LocalDateTime.now());
              List<Long> cars = rental.getCars().stream().map(Car::getId).toList();
              carService.setStatusForCarsFromIdList(cars, CarStatus.AVAILABLE);
              return rental;
            })
            .orElseThrow(() -> new RentalException(RentalExceptionMessage.RENTAL_NOT_FOUND));
  }

  @Override
  public void cancelRental(Long id) {
    rentalRepository.findById(id)
            .map(rental -> {
              validator.checkIfStatusIsCompletedOrCanceled(rental, null);
              rental.setStatus(RentalStatus.CANCELLED);
              List<Long> cars = rental.getCars().stream().map(Car::getId).toList();
              carService.setStatusForCarsFromIdList(cars, CarStatus.AVAILABLE);
              return rental;
            })
            .orElseThrow(() -> new RentalException(RentalExceptionMessage.RENTAL_NOT_FOUND));
  }

  private Rental validateRentalOnSave(RentalWriteModel rental) {
    int rentalFor = rental.getRentalFor();
    RentalStatus status = rental.getStatus();
    Employee employee = validator.checkEmployeeById(rental.getEmployeeId());
    Client client = validator.checkClientById(rental.getClientId());
    Set<Car> cars = validator.checkCarsByIdList(null, rental);

    return new Rental(rentalFor, status, client, employee, cars);
  }

  private Rental validateRentalOnUpdate(Rental previousRental, RentalWriteModel nextRental) {
    int rentalFor = nextRental.getRentalFor();
    RentalStatus status = validator.checkIfStatusIsCompletedOrCanceled(previousRental, nextRental);
    Employee employee = validator.checkEmployeeById(nextRental.getEmployeeId());
    Client client = validator.checkClientById(nextRental.getClientId());
    Set<Car> cars = validator.checkCarsByIdList(previousRental, nextRental);

    previousRental.setRentalFor(rentalFor);
    previousRental.setStatus(status);
    previousRental.setEmployee(employee);
    previousRental.setClient(client);
    previousRental.setCars(cars);

    return previousRental;
  }

  private void updateStatusForCarsFromList(List<Long> previousCars, List<Long> nextCars) {
    List<Long> carsAddedToRental = nextCars.stream()
            .filter(carId -> !previousCars.contains(carId))
            .toList();

    List<Long> carsRemovedFromRental = previousCars.stream()
            .filter(carId -> !nextCars.contains(carId))
            .toList();

    carService.setStatusForCarsFromIdList(carsAddedToRental, CarStatus.RENTAL);
    carService.setStatusForCarsFromIdList(carsRemovedFromRental, CarStatus.AVAILABLE);
  }
}
