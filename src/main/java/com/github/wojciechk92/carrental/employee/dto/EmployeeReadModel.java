package com.github.wojciechk92.carrental.employee.dto;

import com.github.wojciechk92.carrental.employee.Employee;
import com.github.wojciechk92.carrental.employee.EmployeeStatus;
import com.github.wojciechk92.carrental.rental.Rental;

import java.util.List;

public class EmployeeReadModel {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private int tel;
  private EmployeeStatus status;
  private List<Long> rentalIdList;

  public EmployeeReadModel(Employee employee) {
    this.id = employee.getId();
    this.firstName = employee.getPersonalDetails().getFirstName();
    this.lastName = employee.getPersonalDetails().getLastName();
    this.email = employee.getPersonalDetails().getEmail();
    this.tel = employee.getPersonalDetails().getTel();
    this.status = employee.getStatus();
    this.rentalIdList = employee.getRentals().stream()
            .map(Rental::getId)
            .toList();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getTel() {
    return tel;
  }

  public void setTel(int tel) {
    this.tel = tel;
  }

  public EmployeeStatus getStatus() {
    return status;
  }

  public void setStatus(EmployeeStatus status) {
    this.status = status;
  }

  public List<Long> getRentalIdList() {
    return rentalIdList;
  }

  public void setRentalIdList(List<Long> rentalIdList) {
    this.rentalIdList = rentalIdList;
  }
}
