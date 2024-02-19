package com.github.wojciechk92.carrental.common.personalDetails;

public enum PersonalDetailsExceptionMessage {
  EMAIL_IS_NOT_UNIQUE("Email is not unique!"),
  TEL_IS_NOT_UNIQUE("Tel is not unique!");

  private final String message;

  PersonalDetailsExceptionMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
