package com.github.wojciechk92.carrental.car.dto;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.car.CarStatus;

public class CarReadModel {
  private Long id;
  private String make;
  private String model;
  private int productionYear;
  private CarStatus status;

  public CarReadModel(Car car) {
    this.id = car.getId();
    this.make = car.getMake();
    this.model = car.getModel();
    this.productionYear = car.getProductionYear();
    this.status = car.getStatus();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getProductionYear() {
    return productionYear;
  }

  public void setProductionYear(int productionYear) {
    this.productionYear = productionYear;
  }

  public CarStatus getStatus() {
    return status;
  }

  public void setStatus(CarStatus status) {
    this.status = status;
  }
}
