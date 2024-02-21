package com.github.wojciechk92.carrental.rental.exception;

import com.github.wojciechk92.carrental.common.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RentalExceptionHandler {

  @ExceptionHandler(RentalException.class)
  public ResponseEntity<ExceptionMessage> rentalExceptionHandler(RentalException e) {
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    ExceptionMessage body = new ExceptionMessage();
    RentalExceptionMessage exceptionMessage = e.getExceptionMessage();
    String message = exceptionMessage.getMessage();

    if (RentalExceptionMessage.RENTAL_NOT_FOUND.equals(exceptionMessage)) {
      httpStatus = HttpStatus.NOT_FOUND;
      body.addError("rentalId", message);
    } else if (RentalExceptionMessage.CLIENT_STATUS_IS_NOT_ACTIVE.equals(exceptionMessage)) {
      body.addError("clientId", message);
    } else if (RentalExceptionMessage.EMPLOYEE_STATUS_IS_NOT_EMPLOYED.equals(exceptionMessage)) {
      body.addError("employeeId", message);
    } else if (RentalExceptionMessage.CAR_STATUS_IS_NOT_AVAILABLE.equals(exceptionMessage)) {
      body.addError("carsIdList", message);
    } else if (RentalExceptionMessage.CAR_LIST_IS_EMPTY.equals(exceptionMessage)) {
      body.addError("carsIdList", message);
    } else if (RentalExceptionMessage.RENTAL_STATUS_IS_ALREADY_COMPLETED.equals(exceptionMessage)) {
      httpStatus = HttpStatus.CONFLICT;
      body.addError(null, message);
    } else if (RentalExceptionMessage.RENTAL_STATUS_IS_ALREADY_CANCELED.equals(exceptionMessage)) {
      httpStatus = HttpStatus.CONFLICT;
      body.addError(null, message);
    } else {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      body.addError(null, message);
    }

    return ResponseEntity.status(httpStatus).body(body);
  }
}
