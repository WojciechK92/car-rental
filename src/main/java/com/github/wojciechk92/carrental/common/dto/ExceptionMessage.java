package com.github.wojciechk92.carrental.common.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExceptionMessage {
  private List<Object> errors = new ArrayList<>();

  public List<Object> getErrors() {
    return errors;
  }

  public void setErrors(List<Object> errors) {
    this.errors = errors;
  }

  public void addError(String fieldName, String message) {
    Map<String, String> error = new HashMap<>();
    error.put("field", fieldName);
    error.put("message", message);
    errors.add(error);
  }
}
