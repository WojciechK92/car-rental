package com.github.wojciechk92.carrental.rental.exception;

import com.github.wojciechk92.carrental.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RentalExceptionHandler {

  @ExceptionHandler(RentalException.class)
  public ResponseEntity<ExceptionMessage> rentalExceptionHandler(RentalException e) {
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    ExceptionMessage message = new ExceptionMessage(e.getExceptionMessage().getMessage());

    if (RentalExceptionMessage.RENTAL_NOT_FOUND.equals(e.getExceptionMessage())) {
      httpStatus = HttpStatus.NOT_FOUND;
    }

    return ResponseEntity.status(httpStatus).body(message);
  }
}
