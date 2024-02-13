package com.github.wojciechk92.carrental.car;

import com.github.wojciechk92.carrental.rental.Rental;
import jakarta.persistence.*;

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
  private Set<Rental> rentals;

  public Car() {}

  public Car(String make, String model, int productionYear, CarStatus status) {
    this.make = make;
    this.model = model;
    this.productionYear = productionYear;
    this.status = status;
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
}
