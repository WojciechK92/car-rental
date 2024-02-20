package com.github.wojciechk92.carrental.car.validator;

import com.github.wojciechk92.carrental.car.exception.CarException;
import com.github.wojciechk92.carrental.car.exception.CarExceptionMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CarValidatorImpl implements CarValidator {

  @Override
  public void validateCarYear(int year) {
    if (year > LocalDateTime.now().getYear()) throw new CarException(CarExceptionMessage.CAR_PRODUCTION_DATE_IS_INCORRECT);
  }
}
