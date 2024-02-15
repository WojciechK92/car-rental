package com.github.wojciechk92.carrental.client;

public enum ClientStatus {
  ACTIVE("The client is currently active and using the company's services."),
  INACTIVE("The client is currently not using the company's services."),
  BLOCKED("The client 's account has been blocked.");

  private final String message;

  ClientStatus(String message) {
    this.message = message;
  }

  String getMessage() {
    return message;
  }
}
