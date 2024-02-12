package com.github.wojciechk92.carrental.car.exception;

public class CarException extends RuntimeException {
  private final CarExceptionMessage exceptionMessage;

  public CarException(CarExceptionMessage exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public CarExceptionMessage getExceptionMessage() {
    return exceptionMessage;
  }
}
