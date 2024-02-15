package com.github.wojciechk92.carrental.rental;

import com.github.wojciechk92.carrental.car.Car;
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

import java.util.List;
import java.util.Set;

@Service
class RentalServiceImpl implements RentalService {
  private final RentalRepository rentalRepository;
  private final RentalValidator validator;

  @Autowired
  RentalServiceImpl(RentalRepository rentalRepository, RentalValidator validator) {
    this.rentalRepository = rentalRepository;
    this.validator = validator;
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
    Client client = validator.checkClientById(toSave.getClientId());
    Employee employee = validator.checkEmployeeById(toSave.getEmployeeId());
    Set<Car> cars = validator.checkCarsByIdList(toSave.getCarsIdList());

    Rental rental = toSave.toRental(client, employee, cars);
    Rental result = rentalRepository.save(rental);
    return new RentalReadModel(result);
  }

  @Transactional
  @Override
  public void updateRental(RentalWriteModel toUpdate, Long id) {
    rentalRepository.findById(id)
            .map(rental -> {
              Client client = validator.checkClientById(toUpdate.getClientId());
              Employee employee = validator.checkEmployeeById(toUpdate.getEmployeeId());
              Set<Car> cars = validator.checkCarsByIdList(toUpdate.getCarsIdList());

              rental.setRentalFor(toUpdate.getRentalFor());
              rental.setStatus(toUpdate.getStatus());
              rental.setClient(client);
              rental.setEmployee(employee);
              rental.setCars(cars);
              return rental;
            })
            .orElseThrow(() -> new RentalException(RentalExceptionMessage.RENTAL_NOT_FOUND));
  }

  @Transactional
  @Override
  public void setStatusTo(RentalStatus status, Long id) {
    rentalRepository.findById(id)
            .map(rental -> {
              rental.setStatus(status);
              return rental;
            })
            .orElseThrow(() -> new RentalException(RentalExceptionMessage.RENTAL_NOT_FOUND));
  }
}
