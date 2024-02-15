package com.github.wojciechk92.carrental.rental;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.employee.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "rentals")
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime rentalDate;
  private LocalDateTime returnDate;
  @Min(1)
  private int rentalFor;
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", columnDefinition = "VARCHAR(30)")
  private RentalStatus status;
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;
  @ManyToOne
  @JoinColumn(name="employee_id")
  private Employee employee;
  @ManyToMany
  @JoinTable(name = "rental_car",
          joinColumns = @JoinColumn(name = "rental_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
  private Set<Car> cars;

  public Rental() {}

  public Rental(int rentalFor, RentalStatus status, Client client, Employee employee, Set<Car> cars) {
    this.rentalFor = rentalFor;
    this.status = status;
    this.client = client;
    this.employee = employee;
    this.cars = cars;
  }

  public Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getRentalDate() {
    return rentalDate;
  }

  void setRentalDate(LocalDateTime orderDate) {
    this.rentalDate = orderDate;
  }

  public LocalDateTime getReturnDate() {
    return returnDate;
  }

  void setReturnDate(LocalDateTime returnDate) {
    this.returnDate = returnDate;
  }

  public int getRentalFor() {
    return rentalFor;
  }

  void setRentalFor(int rentalFor) {
    this.rentalFor = rentalFor;
  }

  public RentalStatus getStatus() {
    return status;
  }

  void setStatus(RentalStatus status) {
    this.status = status;
  }

  public Client getClient() {
    return client;
  }

  void setClient(Client client) {
    this.client = client;
  }

  public Employee getEmployee() {
    return employee;
  }

  void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Set<Car> getCars() {
    return cars;
  }

  void setCars(Set<Car> cars) {
    this.cars = cars;
  }

  @PrePersist
  private void prePersist() {
    rentalDate = LocalDateTime.now();
  }
}
