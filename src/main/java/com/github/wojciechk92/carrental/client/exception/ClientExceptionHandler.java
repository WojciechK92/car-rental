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
    ExceptionMessage body = new ExceptionMessage();
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    ClientExceptionMessage exceptionMessage = e.getExceptionMessage();
    String message = e.getExceptionMessage().getMessage();

    if (ClientExceptionMessage.CLIENT_NOT_FOUND.equals(exceptionMessage)) {
      httpStatus = HttpStatus.NOT_FOUND;
      body.addError("clientId", message);
    } else if (ClientExceptionMessage.EMAIL_IS_NOT_UNIQUE.equals(exceptionMessage)) {
      body.addError("email", message);
    } else if (ClientExceptionMessage.TEL_IS_NOT_UNIQUE.equals(exceptionMessage)) {
      body.addError("tel", message);
    } else {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      body.addError(null, message);
    }

    return ResponseEntity.status(httpStatus).body(body);
  }
}
