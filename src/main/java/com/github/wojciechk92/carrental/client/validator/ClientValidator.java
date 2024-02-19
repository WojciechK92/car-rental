package com.github.wojciechk92.carrental.client.validator;

public interface ClientValidator {

  boolean validateUniqueEmail(String email);

  boolean validateUniqueTel(int tel);
}
