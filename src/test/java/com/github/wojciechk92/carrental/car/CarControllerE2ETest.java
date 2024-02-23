package com.github.wojciechk92.carrental.car;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.car.dto.CarWriteModel;
import com.github.wojciechk92.carrental.car.exception.CarExceptionMessage;
import com.github.wojciechk92.carrental.common.dto.ExceptionMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarControllerE2ETest {

  @LocalServerPort
  private int port;

  @Autowired
  private CarRepository carRepository;

  @Test
  @DisplayName("Http GET request to '/cars/{id}' returns the expected car.")
  void getCar() {
    // given
    Car carBefore = createCar(true, false);
    CarReadModel carAfter = saveCarToRepository(carBefore);

    // when
    TestRestTemplate restTemplate = new TestRestTemplate();
    CarReadModel response = restTemplate
            .getForObject("http://localhost:" + port + "/cars/" + carAfter.getId(), CarReadModel.class);

    // then
    assertThat(response)
            .isInstanceOf(CarReadModel.class)
            .hasFieldOrPropertyWithValue("make", carAfter.getMake())
            .hasFieldOrPropertyWithValue("model", carAfter.getModel())
            .hasFieldOrPropertyWithValue("productionYear", carAfter.getProductionYear())
            .hasFieldOrPropertyWithValue("pricePerDay", carAfter.getPricePerDay())
            .hasFieldOrPropertyWithValue("status", carAfter.getStatus());
  }

  private Car createCar(boolean yearIsCorrect, boolean secondVersion) {
    if (secondVersion) return new Car("toyota", "avensis", yearIsCorrect ? 2023 : 2035, 559.79, CarStatus.INACTIVE);
    return new Car("audi", "a8", yearIsCorrect ? 2018 : 2035, 789.79, CarStatus.AVAILABLE);
  }

  private CarReadModel saveCarToRepository(Car carBefore) {
    Car carAfter = carRepository.save(carBefore);
    return new CarReadModel(carAfter);
  }
}