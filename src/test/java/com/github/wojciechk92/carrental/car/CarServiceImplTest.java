package com.github.wojciechk92.carrental.car;

import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.car.dto.CarWriteModel;
import com.github.wojciechk92.carrental.car.exception.CarException;
import com.github.wojciechk92.carrental.car.exception.CarExceptionMessage;
import com.github.wojciechk92.carrental.car.validator.CarValidatorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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

  @Test
  @DisplayName("Should throw CarException when production year is wrong.")
  void updateCar_wrongProductionYear_throwCarException() {
    // given
    CarWriteModel carToUpdate = generateCarWriteModel("audi", "a6", 2025, 919.99, CarStatus.INACTIVE);
    CarValidatorImpl carValidator = new CarValidatorImpl();

    // system under test
    CarServiceImpl toTest = new CarServiceImpl(null, carValidator);

    //when
    Throwable exception = catchThrowable(() -> toTest.updateCar(carToUpdate, 0L));

    // then
    assertThat(((CarException) exception).getExceptionMessage())
            .isEqualTo(CarExceptionMessage.CAR_PRODUCTION_DATE_IS_INCORRECT);
  }

  @Test
  @DisplayName("Should throw CarException when production year is wrong.")
  void updateCar_correctProductionYear_wrongCarId_throwCarException() {
    // given
    CarWriteModel carToUpdate = generateCarWriteModel("audi", "a6", 2022, 919.99, CarStatus.INACTIVE);
    CarValidatorImpl carValidator = new CarValidatorImpl();
    CarRepository mockCarRepository = mock(CarRepository.class);
    when(mockCarRepository.findById(anyLong())).thenReturn(Optional.empty());

    // system under test
    CarServiceImpl toTest = new CarServiceImpl(mockCarRepository, carValidator);

    //when
    Throwable exception = catchThrowable(() -> toTest.updateCar(carToUpdate, 1L));

    // then
    assertThat(((CarException) exception).getExceptionMessage())
            .isEqualTo(CarExceptionMessage.CAR_NOT_FOUND);
  }

  @Test
  @DisplayName("Should update car when production year and car id are correct.")
  void updateCar_correctProductionYear_correctCarId() {
    // given
    CarWriteModel carToUpdate = generateCarWriteModel("audi", "a6", 2022, 919.99, CarStatus.INACTIVE);
    Car carFromDb = new Car("ford", "fiesta", 2013, 79.98, CarStatus.RENTAL);

    CarValidatorImpl carValidator = new CarValidatorImpl();
    CarRepository mockCarRepository = mock(CarRepository.class);
    when(mockCarRepository.findById(anyLong())).thenReturn(Optional.of(carFromDb));

    // system under test
    CarServiceImpl toTest = new CarServiceImpl(mockCarRepository, carValidator);

    //when
    toTest.updateCar(carToUpdate, 1L);

    // then
    assertThat(carFromDb)
            .hasFieldOrPropertyWithValue("make", carToUpdate.getMake())
            .hasFieldOrPropertyWithValue("model", carToUpdate.getModel())
            .hasFieldOrPropertyWithValue("productionYear", carToUpdate.getProductionYear())
            .hasFieldOrPropertyWithValue("pricePerDay", carToUpdate.getPricePerDay())
            .hasFieldOrPropertyWithValue("status", carToUpdate.getStatus());
  }

  @Test
  @DisplayName("Should throw CarException when carId is wrong.")
  void setStatusTo_wrongCarId_throwCarException() {
    // given
    CarWriteModel carToUpdate = generateCarWriteModel("audi", "a6", 2022, 919.99, CarStatus.INACTIVE);
    CarRepository mockCarRepository = mock(CarRepository.class);
    when(mockCarRepository.findById(anyLong())).thenReturn(Optional.empty());

    // system under test
    CarServiceImpl toTest = new CarServiceImpl(mockCarRepository, null);

    //when
    Throwable exception = catchThrowable(() -> toTest.setStatusTo(CarStatus.AVAILABLE, 1L));

    // then
    assertThat(((CarException) exception).getExceptionMessage())
            .isEqualTo(CarExceptionMessage.CAR_NOT_FOUND);
  }

  @Test
  @DisplayName("Should update car status when production year and car id are correct.")
  void setStatusTo_correctProductionYear_correctCarId() {
    // given
    Car carFromDb = generateCar("ford", "fiesta", 2013, 79.98, CarStatus.RENTAL);
    CarStatus status = CarStatus.AVAILABLE;

    CarValidatorImpl carValidator = new CarValidatorImpl();
    CarRepository mockCarRepository = mock(CarRepository.class);
    when(mockCarRepository.findById(anyLong())).thenReturn(Optional.of(carFromDb));

    // system under test
    CarServiceImpl toTest = new CarServiceImpl(mockCarRepository, carValidator);

    //when
    toTest.setStatusTo(status, 1L);

    // then
    assertThat(carFromDb)
            .hasFieldOrPropertyWithValue("status", status);
  }

  @Test
  @DisplayName("Should update car status for all cars on the list.")
  void setStatusForCarsFromIdList() {
    // given
    CarStatus status = CarStatus.IN_SERVICE;
    Car car1 = generateCar("audi", "a6", 2025, 919.99, CarStatus.INACTIVE);
    Car car2 = generateCar("ford", "fiesta", 2013, 79.98, CarStatus.RENTAL);
    List<Long> idList = List.of(1L, 2L);
    List<Car> carList = List.of(car1, car2);

    CarRepository mockCarRepository = mock(CarRepository.class);
    when(mockCarRepository.findAllByIdIsIn(anyList())).thenReturn(carList);

    // system under test
    CarServiceImpl toTest = new CarServiceImpl(mockCarRepository, null);

    // when
    toTest.setStatusForCarsFromIdList(idList, status);

    // then
    assertThat(carList)
            .extracting(Car::getStatus)
            .allMatch(status::equals);
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

  private Car generateCar(String make, String model, int productionYear, double pricePerDay, CarStatus status) {
    return new Car(make, model, productionYear, pricePerDay, status);
  }
}