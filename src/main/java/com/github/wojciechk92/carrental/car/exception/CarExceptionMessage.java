package com.github.wojciechk92.carrental.car.exception;

public enum CarExceptionMessage {
  CAR_NOT_FOUND("Car with given id does not exist!"),
  LIST_CONTAINS_UNAVAILABLE_CAR("List contains an unavailable car!"),
  CAR_PRODUCTION_DATE_IS_INVALID("Car production date is invalid!");

  private final String message;

  CarExceptionMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
