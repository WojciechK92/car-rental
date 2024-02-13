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
  @ManyToMany
  @JoinTable(name = "rental_car",
          joinColumns = @JoinColumn(name = "rental_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
  private Set<Car> cars;

  public Rental() {}

  public Rental(LocalDateTime rentalDate, LocalDateTime returnDate) {
    this.rentalDate = rentalDate;
    this.returnDate = returnDate;
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

  public void setClient(Client client) {
    this.client = client;
  }
}
