package com.github.wojciechk92.carrental.rental.exception;

public enum RentalExceptionMessage {
  RENTAL_NOT_FOUND("The rental with given id does not exist!");

  private final String message;

  RentalExceptionMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
