package com.github.wojciechk92.carrental.car;

import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.car.dto.CarWriteModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarService {

  List<CarReadModel> getCars(Pageable pageable);

  List<CarReadModel> getCarsByIdList(List<Long> list);

  CarReadModel getCar(Long id);

  CarReadModel createCar(CarWriteModel car);

}
