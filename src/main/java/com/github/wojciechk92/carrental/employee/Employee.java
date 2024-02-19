package com.github.wojciechk92.carrental.employee;

import com.github.wojciechk92.carrental.common.embeddable.PersonalDetails;
import com.github.wojciechk92.carrental.employee.dto.EmployeeReadModel;
import com.github.wojciechk92.carrental.rental.Rental;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private PersonalDetails personalDetails;
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", columnDefinition = "VARCHAR(30)")
  private EmployeeStatus status;
  @OneToMany(mappedBy = "employee")
  private Set<Rental> rentals = new HashSet<>();

  public Employee() {
  }

  public Employee(String firstName, String lastName, String email, int tel, EmployeeStatus status) {
    this.personalDetails = new PersonalDetails(firstName, lastName, email, tel);
    this.status = status;
  }

  public Employee(EmployeeReadModel employee) {
    this.id = employee.getId();
    this.personalDetails = new PersonalDetails(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getTel());
    this.status = employee.getStatus();
  }

  public Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  public PersonalDetails getPersonalDetails() {
    return personalDetails;
  }

  void setPersonalDetails(PersonalDetails personalDetails) {
    this.personalDetails = personalDetails;
  }

  public EmployeeStatus getStatus() {
    return status;
  }

  void setStatus(EmployeeStatus status) {
    this.status = status;
  }

  public Set<Rental> getRentals() {
    return rentals;
  }

  void setRentals(Set<Rental> rentals) {
    this.rentals = rentals;
  }
}
