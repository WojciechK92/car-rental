package com.github.wojciechk92.carrental.car;

import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.car.dto.CarWriteModel;
import com.github.wojciechk92.carrental.car.exception.CarException;
import com.github.wojciechk92.carrental.car.exception.CarExceptionMessage;
import com.github.wojciechk92.carrental.car.validator.CarValidatorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

  @Test
  @DisplayName("Should throw CarException when production year is wrong.")
  void createCar_wrongProductionYear_throwCarException() {
    // given
    CarWriteModel carToSave = generateCarWriteModel("audi", "a6", 2025, 919.99, CarStatus.INACTIVE);
    CarValidatorImpl carValidator = new CarValidatorImpl();

    // system under test
    CarServiceImpl toTest = new CarServiceImpl(null, carValidator);

    //when
    Throwable exception = catchThrowable(() -> toTest.createCar(carToSave));

    // then
    assertThat(((CarException) exception).getExceptionMessage())
            .isEqualTo(CarExceptionMessage.CAR_PRODUCTION_DATE_IS_INCORRECT);
  }

  @Test
  @DisplayName("Should return CarReadModel when production year is correct.")
  void createCar_correctProductionYear_return_CarReadModel() {
    // given
    CarWriteModel carToSave = generateCarWriteModel("bmw", "e60", 2004, 19.99, CarStatus.AVAILABLE);
    Car carToReturn = carToSave.toCar();
    carToReturn.setId(1L);

    CarRepository mockCarRepository = mock(CarRepository.class);
    when(mockCarRepository.save(any(Car.class))).thenReturn(carToReturn);
    CarValidatorImpl carValidator = new CarValidatorImpl();

    // system under test
    CarServiceImpl toTest = new CarServiceImpl(mockCarRepository, carValidator);

    //when
    CarReadModel car = toTest.createCar(carToSave);

    // then
    verify(mockCarRepository, times(1)).save(any(Car.class));
    assertThat(car)
            .hasFieldOrPropertyWithValue("make", "bmw");
  }

  private CarWriteModel generateCarWriteModel(String make, String model, int productionYear, double pricePerDay, CarStatus status) {
    CarWriteModel car = new CarWriteModel();
    car.setMake(make);
    car.setModel(model);
    car.setProductionYear(productionYear);
    car.setPricePerDay(pricePerDay);
    car.setStatus(status);

    return car;
  }
}