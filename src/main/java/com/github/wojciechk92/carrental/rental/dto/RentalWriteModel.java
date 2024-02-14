package com.github.wojciechk92.carrental.rental.dto;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.employee.Employee;
import com.github.wojciechk92.carrental.rental.Rental;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class RentalWriteModel {
  private LocalDateTime rentalDate;
  private LocalDateTime returnDate;
  private int rentalFor;
  private Long clientId;
  private Long employeeId;
  private List<Long> carsIdList;

  public RentalWriteModel() {}

  public LocalDateTime getRentalDate() {
    return rentalDate;
  }

  public void setRentalDate(LocalDateTime rentalDate) {
    this.rentalDate = rentalDate;
  }

  public LocalDateTime getReturnDate() {
    return returnDate;
  }

  public int getRentalFor() {
    return rentalFor;
  }

  public void setRentalFor(int rentalFor) {
    this.rentalFor = rentalFor;
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

  public Rental toRental(Client client, Employee employee, Set<Car> cars) {
    return new Rental(rentalDate, returnDate, rentalFor, client, employee, cars);
  }
}
