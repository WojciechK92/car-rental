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
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    ExceptionMessage body = new ExceptionMessage();
    CarExceptionMessage exceptionMessage = e.getExceptionMessage();
    String message = e.getExceptionMessage().getMessage();

    if (CarExceptionMessage.CAR_NOT_FOUND.equals(exceptionMessage)) {
      httpStatus = HttpStatus.NOT_FOUND;
      body.addError("carId", message);
    } else if (CarExceptionMessage.LIST_CONTAINS_WRONG_CAR_ID.equals(exceptionMessage)) {
      body.addError("carList", message);
    } else if (CarExceptionMessage.CAR_PRODUCTION_DATE_IS_INCORRECT.equals(exceptionMessage)){
      body.addError("productionYear", message);
    } else {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      body.addError(null, message);
    }

    return ResponseEntity.status(httpStatus).body(body);
  }
}
