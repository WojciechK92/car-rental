package com.github.wojciechk92.carrental.car.exception;

public enum CarExceptionMessage {
  CAR_NOT_FOUND("The car with given id does not exist!");

  private final String message;

  CarExceptionMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
