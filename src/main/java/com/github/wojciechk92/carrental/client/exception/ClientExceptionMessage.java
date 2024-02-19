package com.github.wojciechk92.carrental.client.exception;

public enum ClientExceptionMessage {
  CLIENT_NOT_FOUND("Client with given id does not exist!"),
  EMAIL_IS_NOT_UNIQUE("Email is not unique!"),
  TEL_IS_NOT_UNIQUE("Tel is not unique!");

  private final String message;

  ClientExceptionMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
