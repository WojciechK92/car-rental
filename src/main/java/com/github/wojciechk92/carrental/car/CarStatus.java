package com.github.wojciechk92.carrental.car;

public enum CarStatus {
  RESERVED("Car is reserved."),
  AVAILABLE("Car is available for rental."),
  RENTAL("Car is currently rented."),
  IN_SERVICE("Car is currently not available for rental."),
  INACTIVE("Car is no longer part of the fleet.");

  private final String message;

  CarStatus(String message) {
    this.message = message;
  }

  String getMessage() {
    return message;
  }
}
