package com.github.wojciechk92.carrental.client.exception;

public enum ClientExceptionMessage {
  CLIENT_NOT_FOUND("The client with given id does not exist!");

  private final String message;

  ClientExceptionMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
