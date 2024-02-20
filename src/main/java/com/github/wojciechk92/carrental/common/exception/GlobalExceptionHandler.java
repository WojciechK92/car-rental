package com.github.wojciechk92.carrental.common.exception;

import com.github.wojciechk92.carrental.common.dto.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
    ExceptionMessage message = new ExceptionMessage();
    ex.getBindingResult().getFieldErrors()
            .forEach(field -> message.addError(field.getField(), field.getDefaultMessage()));
    return ResponseEntity.status(status).headers(headers).body(message);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
    ExceptionMessage message = new ExceptionMessage();
    message.addError(null, ex.getMessage());
    return ResponseEntity.status(status).headers(headers).body(message);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ExceptionMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    ExceptionMessage message = new ExceptionMessage();
    message.addError(e.getName(), e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
  }

}
