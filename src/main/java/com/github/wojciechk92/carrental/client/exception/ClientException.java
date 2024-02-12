package com.github.wojciechk92.carrental.client.exception;

public class ClientException extends RuntimeException {
  private final ClientExceptionMessage exceptionMessage;

  public ClientException(ClientExceptionMessage exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public ClientExceptionMessage getExceptionMessage() {
    return exceptionMessage;
  }
}
