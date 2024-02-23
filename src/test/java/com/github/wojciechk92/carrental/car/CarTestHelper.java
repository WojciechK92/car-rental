package com.github.wojciechk92.carrental.car;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.car.dto.CarWriteModel;
import com.github.wojciechk92.carrental.car.exception.CarExceptionMessage;
import com.github.wojciechk92.carrental.common.dto.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class CarTestHelper {

  private final CarRepository carRepository;

  @Autowired
  CarTestHelper(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  Car createCar(boolean nextCar) {
    if (nextCar) return new Car("toyota", "avensis", 2023, 559.79, CarStatus.INACTIVE);
    return new Car("audi", "a8", 2018, 789.79, CarStatus.AVAILABLE);
  }

  CarReadModel saveCarToRepository(Car carBefore) {
    Car carAfter = carRepository.save(carBefore);
    return new CarReadModel(carAfter);
  }

  CarWriteModel createCarWriteModel(Car carBefore){
    CarWriteModel carAfter = new CarWriteModel();
    carAfter.setMake(carBefore.getMake());
    carAfter.setModel(carBefore.getModel());
    carAfter.setProductionYear(carBefore.getProductionYear());
    carAfter.setPricePerDay(carBefore.getPricePerDay());
    carAfter.setStatus(carBefore.getStatus());

    return carAfter;
  }

  CarReadModel createCarReadModel(Car carBefore){
    CarReadModel carAfter = new CarReadModel();
    carAfter.setMake(carBefore.getMake());
    carAfter.setModel(carBefore.getModel());
    carAfter.setProductionYear(carBefore.getProductionYear());
    carAfter.setPricePerDay(carBefore.getPricePerDay());
    carAfter.setStatus(carBefore.getStatus());
    carAfter.setRentalIdList(List.of());

    return carAfter;
  }

  String mapObjectToString(Object toMap) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(toMap);
  }

  ExceptionMessage createExceptionMessage(String fieldName, CarExceptionMessage exceptionMessage) {
    ExceptionMessage message = new ExceptionMessage();
    message.addError(fieldName, exceptionMessage.getMessage());
    return message;
  }
}
