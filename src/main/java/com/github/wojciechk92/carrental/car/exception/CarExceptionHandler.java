package com.github.wojciechk92.carrental.car.exception;

import com.github.wojciechk92.carrental.common.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CarExceptionHandler {

  @ExceptionHandler(CarException.class)
  public ResponseEntity<ExceptionMessage> carExceptionHandler(CarException e) {
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    ExceptionMessage body = new ExceptionMessage();
    String message = e.getExceptionMessage().getMessage();

    if (CarExceptionMessage.CAR_NOT_FOUND.equals(e.getExceptionMessage())) {
      httpStatus = HttpStatus.NOT_FOUND;
      body.addError("carId", message);
    } else if (CarExceptionMessage.LIST_CONTAINS_UNAVAILABLE_CAR.equals(e.getExceptionMessage())) {
      httpStatus = HttpStatus.BAD_REQUEST;
      body.addError("carList", message);
    } else {
      body.addError("clientId", message);
    }

    return ResponseEntity.status(httpStatus).body(body);
  }
}
