package com.github.wojciechk92.carrental.car;

import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.rental.Rental;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String make;
  private String model;
  private int productionYear;
  @Enumerated(EnumType.STRING)
  private CarStatus status;
  @ManyToMany(mappedBy = "cars")
  private Set<Rental> rentals = new HashSet<>();

  public Car() {}

  public Car(String make, String model, int productionYear, CarStatus status) {
    this.make = make;
    this.model = model;
    this.productionYear = productionYear;
    this.status = status;
  }

  public Car(CarReadModel car) {
    this.make = car.getMake();
    this.model = car.getModel();
    this.productionYear = car.getProductionYear();
    this.status = car.getStatus();
  }

  public Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  public String getMake() {
    return make;
  }

  void setMake(String make) {
    this.make = make;
  }

  public String getModel() {
    return model;
  }

  void setModel(String model) {
    this.model = model;
  }

  public int getProductionYear() {
    return productionYear;
  }

  void setProductionYear(int year) {
    this.productionYear = year;
  }

  public CarStatus getStatus() {
    return status;
  }

  void setStatus(CarStatus status) {
    this.status = status;
  }

  public Set<Rental> getRentals() {
    return rentals;
  }

  void setRentals(Set<Rental> rentals) {
    this.rentals = rentals;
  }
}
