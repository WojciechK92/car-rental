package com.github.wojciechk92.carrental.rental.exception;

public class RentalException extends RuntimeException {
  private final RentalExceptionMessage exceptionMessage;

  public RentalException(RentalExceptionMessage exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public RentalExceptionMessage getExceptionMessage() {
    return exceptionMessage;
  }
}
