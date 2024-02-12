package com.github.wojciechk92.carrental.car;

public enum CarStatus {
  ACTIVE("The car is ready for rental!"),
  INACTIVE("The car is rented!");

  private final String message;

  CarStatus(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
