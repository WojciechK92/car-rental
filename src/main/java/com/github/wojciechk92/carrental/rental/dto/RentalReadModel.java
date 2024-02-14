package com.github.wojciechk92.carrental.rental.dto;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.client.dto.ClientReadModel;
import com.github.wojciechk92.carrental.employee.dto.EmployeeReadModel;
import com.github.wojciechk92.carrental.rental.Rental;

import java.time.LocalDateTime;
import java.util.List;

public class RentalReadModel {
  private Long id;
  private LocalDateTime rentalDate;
  private LocalDateTime returnDate;
  private Long clientId;
  private Long employeeId;
  private List<Long> carsIdList;

  public RentalReadModel(Rental rental) {
    this.id = rental.getId();
    this.rentalDate = rental.getRentalDate();
    this.returnDate = rental.getReturnDate();
    this.clientId = rental.getClient().getId();
    this.employeeId = rental.getEmployee().getId();
    this.carsIdList = rental.getCars().stream()
            .map(Car::getId)
            .toList();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getRentalDate() {
    return rentalDate;
  }

  public void setRentalDate(LocalDateTime rentalDate) {
    this.rentalDate = rentalDate;
  }

  public LocalDateTime getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDateTime returnDate) {
    this.returnDate = returnDate;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public List<Long> getCarsIdList() {
    return carsIdList;
  }

  public void setCarsIdList(List<Long> carsIdList) {
    this.carsIdList = carsIdList;
  }
}
