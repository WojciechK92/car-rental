package com.github.wojciechk92.carrental.car.exception;

public enum CarExceptionMessage {
  CAR_NOT_FOUND("Car with given id does not exist!"),
  LIST_CONTAINS_WRONG_CAR_ID("List contains wrong car id!"),
  CAR_PRODUCTION_DATE_IS_INCORRECT("Car production date is incorrect!");

  private final String message;

  CarExceptionMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
