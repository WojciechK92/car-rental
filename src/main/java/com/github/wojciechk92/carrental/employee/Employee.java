package com.github.wojciechk92.carrental.employee;

import com.github.wojciechk92.carrental.rental.Rental;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private int tel;
  @OneToMany(mappedBy = "employee")
  private Set<Rental> rentals;

  public Employee() {
  }

  public Employee(String firstName, String lastName, String email, int tel) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.tel = tel;
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
}
