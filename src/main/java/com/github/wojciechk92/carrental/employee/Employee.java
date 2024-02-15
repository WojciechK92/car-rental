package com.github.wojciechk92.carrental.employee;

import com.github.wojciechk92.carrental.employee.dto.EmployeeReadModel;
import com.github.wojciechk92.carrental.rental.Rental;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
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
  @OneToMany(mappedBy = "employee")
  private Set<Rental> rentals = new HashSet<>();

  public Employee() {
  }

  public Employee(String firstName, String lastName, String email, int tel) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.tel = tel;
  }

  public Employee(EmployeeReadModel employee) {
    this.id = employee.getId();
    this.firstName = employee.getFirstName();
    this.lastName = employee.getLastName();
    this.email = employee.getEmail();
    this.tel = employee.getTel();
  }

  public Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  void setEmail(String email) {
    this.email = email;
  }

  public int getTel() {
    return tel;
  }

  void setTel(int tel) {
    this.tel = tel;
  }

  public Set<Rental> getRentals() {
    return rentals;
  }

  void setRentals(Set<Rental> rentals) {
    this.rentals = rentals;
  }
}
