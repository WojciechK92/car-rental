package com.github.wojciechk92.carrental.employee.exception;

public enum EmployeeExceptionMessage {
  EMPLOYEE_NOT_FOUND("The employee with given id does not exist!");

  private final String message;

  EmployeeExceptionMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
