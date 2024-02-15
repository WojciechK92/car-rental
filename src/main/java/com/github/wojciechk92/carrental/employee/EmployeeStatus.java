package com.github.wojciechk92.carrental.employee;

public enum EmployeeStatus {
  EMPLOYED("The employee is currently employed at the company."),
  ON_LEAVE("The employee is currently on leave."),
  DISMISSED("The employee is no longer employed at the company.");

  private final String message;

  EmployeeStatus(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
