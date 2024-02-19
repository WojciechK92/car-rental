package com.github.wojciechk92.carrental.rental.exception;

public enum RentalExceptionMessage {
  RENTAL_NOT_FOUND("Rental with given id does not exist!"),
  EMPLOYEE_STATUS_IS_NOT_EMPLOYED("Employee status is not employed!"),
  CLIENT_STATUS_IS_NOT_ACTIVE("Client status is not active!"),
  CAR_STATUS_IS_NOT_AVAILABLE("Car status is not available!");

  private final String message;

  RentalExceptionMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
