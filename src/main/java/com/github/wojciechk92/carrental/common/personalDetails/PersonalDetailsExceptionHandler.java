package com.github.wojciechk92.carrental.common.personalDetails;

import com.github.wojciechk92.carrental.common.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PersonalDetailsExceptionHandler {

  @ExceptionHandler(PersonalDetailsException.class)
  public ResponseEntity<ExceptionMessage> personalDetailsExceptionHandler(PersonalDetailsException e) {
    ExceptionMessage body = new ExceptionMessage();
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    PersonalDetailsExceptionMessage exceptionMessage = e.getExceptionMessage();
    String message = e.getExceptionMessage().getMessage();

    if (PersonalDetailsExceptionMessage.EMAIL_IS_NOT_UNIQUE.equals(exceptionMessage)) {
      httpStatus = HttpStatus.BAD_REQUEST;
      body.addError("email", message);
    } else if (PersonalDetailsExceptionMessage.TEL_IS_NOT_UNIQUE.equals(exceptionMessage)) {
      httpStatus = HttpStatus.BAD_REQUEST;
      body.addError("tel", message);
    } else {
      body.addError(null, message);
    }

    return ResponseEntity.status(httpStatus).body(body);
  }
}
