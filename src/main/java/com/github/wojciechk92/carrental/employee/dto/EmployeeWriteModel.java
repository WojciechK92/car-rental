package com.github.wojciechk92.carrental.employee.dto;

import com.github.wojciechk92.carrental.employee.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class EmployeeWriteModel {
  @NotBlank
  @Length(min = 3, max = 30)
  private String firstName;
  @NotBlank
  @Length(min = 3, max = 30)
  private String lastName;
  @NotBlank
  @Email
  @Length(max = 50)
  private String email;
  @Min(500_000_000)
  @Max(999_999_999)
  private int tel;


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

  public Employee toEmployee() {
    return new Employee(firstName, lastName,email, tel);
  }

}
