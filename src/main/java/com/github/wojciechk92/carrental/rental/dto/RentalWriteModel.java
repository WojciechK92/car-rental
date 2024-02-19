package com.github.wojciechk92.carrental.rental.dto;

import com.github.wojciechk92.carrental.rental.RentalStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RentalWriteModel {
  @Min(1)
  private int rentalFor;
  @NotNull
  private RentalStatus status;
  @NotNull
  private Long clientId;
  @NotNull
  private Long employeeId;
  @NotNull
  private List<Long> carsIdList;

  public RentalWriteModel() {}

  public int getRentalFor() {
    return rentalFor;
  }

  public void setRentalFor(int rentalFor) {
    this.rentalFor = rentalFor;
  }

  public RentalStatus getStatus() {
    return status;
  }

  public void setStatus(RentalStatus status) {
    this.status = status;
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
