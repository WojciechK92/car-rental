package com.github.wojciechk92.carrental.client.exception;

import com.github.wojciechk92.carrental.common.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionHandler {

  @ExceptionHandler(ClientException.class)
  public ResponseEntity<ExceptionMessage> clientExceptionHandler(ClientException e) {
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    ExceptionMessage message = new ExceptionMessage();

    if (ClientExceptionMessage.CLIENT_NOT_FOUND.equals(e.getExceptionMessage())) {
      httpStatus = HttpStatus.NOT_FOUND;
      message.addError("clientId", e.getExceptionMessage().getMessage());
    } else {
      message.addError(null, e.getExceptionMessage().getMessage());
    }

    return ResponseEntity.status(httpStatus).body(message);
  }
}
