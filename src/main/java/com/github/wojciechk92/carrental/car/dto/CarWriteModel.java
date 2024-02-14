package com.github.wojciechk92.carrental.car.dto;

import com.github.wojciechk92.carrental.car.Car;
import com.github.wojciechk92.carrental.car.CarStatus;

public class CarWriteModel {
  private String make;
  private String model;
  private int productionYear;
  private CarStatus status;
  private double pricePerDay;

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

  public double getPricePerDay() {
    return pricePerDay;
  }

  public void setPricePerDay(double pricePerDay) {
    this.pricePerDay = pricePerDay;
  }

  public Car toCar() {
    return new Car(make, model, productionYear, pricePerDay, status);
  }
}
