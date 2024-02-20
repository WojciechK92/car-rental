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
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    PersonalDetailsExceptionMessage exceptionMessage = e.getExceptionMessage();
    String message = e.getExceptionMessage().getMessage();

    if (PersonalDetailsExceptionMessage.EMAIL_IS_NOT_UNIQUE.equals(exceptionMessage)) {
      body.addError("email", message);
    } else if (PersonalDetailsExceptionMessage.TEL_IS_NOT_UNIQUE.equals(exceptionMessage)) {
      body.addError("tel", message);
    } else {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      body.addError(null, message);
    }

    return ResponseEntity.status(httpStatus).body(body);
  }
}
