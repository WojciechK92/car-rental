package com.github.wojciechk92.carrental.rental;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.employee.Employee;
import jakarta.persistence.*;

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
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;
  @ManyToOne
  @JoinColumn(name="employee_id")
  private Employee employee;
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "rental_car",
          joinColumns = @JoinColumn(name = "rental_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
  private Set<Car> cars;

  public Rental() {}

  public Rental(LocalDateTime rentalDate, LocalDateTime returnDate, Client client, Employee employee, Set<Car> cars) {
    this.rentalDate = rentalDate;
    this.returnDate = returnDate;
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
}
