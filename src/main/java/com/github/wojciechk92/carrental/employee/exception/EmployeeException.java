package com.github.wojciechk92.carrental.employee.exception;

public class EmployeeException extends RuntimeException {
  private final EmployeeExceptionMessage exceptionMessage;

  public EmployeeException(EmployeeExceptionMessage exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public EmployeeExceptionMessage getExceptionMessage() {
    return exceptionMessage;
  }
}
