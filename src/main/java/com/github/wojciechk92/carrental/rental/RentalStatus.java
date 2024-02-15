package com.github.wojciechk92.carrental.rental;

public enum RentalStatus {
  RESERVED("The car has been reserved by a customer but has not yet been picked up."),
  IN_PROGRESS("The car is currently rented out to a customer."),
  COMPLETED("The car has been returned to the rental company by the customer."),
  CANCELLED("The car reservation has been cancelled by the customer or the rental company."),
  DELAYED("The car has not been returned by the due date.");

  private final String message;

  RentalStatus(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
