package com.github.wojciechk92.carrental.common.personalDetails;

public class PersonalDetailsException extends RuntimeException {
  private final PersonalDetailsExceptionMessage exceptionMessage;

  public PersonalDetailsException(PersonalDetailsExceptionMessage exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public PersonalDetailsExceptionMessage getExceptionMessage() {
    return exceptionMessage;
  }
}
