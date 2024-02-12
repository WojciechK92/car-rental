package com.github.wojciechk92.carrental.car;

import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.car.dto.CarWriteModel;
import com.github.wojciechk92.carrental.car.exception.CarException;
import com.github.wojciechk92.carrental.car.exception.CarExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CarServiceImpl implements CarService {
  private final CarRepository carRepository;

  @Autowired
  CarServiceImpl(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  @Override
  public List<CarReadModel> getCars(Pageable pageable) {
    return carRepository.findAll(pageable).getContent().stream()
            .map(CarReadModel::new)
            .toList();
  }

  @Override
  public CarReadModel getCar(Long id) {
    return carRepository.findById(id)
            .map(CarReadModel::new)
            .orElseThrow(() -> new CarException(CarExceptionMessage.CAR_NOT_FOUND));
  }

  @Override
  public CarReadModel createCar(CarWriteModel toSave) {
    Car result = carRepository.save(toSave.toCar());
    return new CarReadModel(result);
  }
}
