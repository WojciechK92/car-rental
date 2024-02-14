package com.github.wojciechk92.carrental.car.exception;

import com.github.wojciechk92.carrental.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CarExceptionHandler {

  @ExceptionHandler(CarException.class)
  public ResponseEntity<ExceptionMessage> carExceptionHandler(CarException e) {
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    ExceptionMessage message = new ExceptionMessage(e.getExceptionMessage().getMessage());

    if (CarExceptionMessage.CAR_NOT_FOUND.equals(e.getExceptionMessage())) {
      httpStatus = HttpStatus.NOT_FOUND;
    }

    return ResponseEntity.status(httpStatus).body(message);
  }
}
