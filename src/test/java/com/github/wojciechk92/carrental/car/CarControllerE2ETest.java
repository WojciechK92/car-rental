package com.github.wojciechk92.carrental.car;

import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.car.dto.CarWriteModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarControllerE2ETest {

  @LocalServerPort
  private int port;
  @Autowired
  private CarRepository carRepository;
  @Autowired
  private CarTestHelper carTestHelper;
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DisplayName("Http GET request to '/cars/{id}' returns the expected car.")
  void httpGet_to_getCarMethod_returns_given_car() {
    // given
    Car carBefore = carTestHelper.createCar(false);
    CarReadModel carAfter = carTestHelper.saveCarToRepository(carBefore);

    // when
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

  @Test
  @DisplayName("Http POST request to '/cars' creates and returns new car.")
  void httpPost_to_createCarMethod_returns_new_car() {
    // given
    Car carBefore = carTestHelper.createCar(false);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Car> request = new HttpEntity<>(carBefore, headers);

    // when
    CarReadModel response = restTemplate
            .postForObject(URI.create("http://localhost:" + port + "/cars"), request, CarReadModel.class);

    // then
    assertThat(response)
            .isInstanceOf(CarReadModel.class)
            .hasFieldOrPropertyWithValue("make", carBefore.getMake())
            .hasFieldOrPropertyWithValue("model", carBefore.getModel())
            .hasFieldOrPropertyWithValue("productionYear", carBefore.getProductionYear())
            .hasFieldOrPropertyWithValue("pricePerDay", carBefore.getPricePerDay())
            .hasFieldOrPropertyWithValue("status", carBefore.getStatus());

    assertThat(carRepository.findById(1L).get())
            .isInstanceOf(Car.class)
            .hasFieldOrPropertyWithValue("make", carBefore.getMake())
            .hasFieldOrPropertyWithValue("model", carBefore.getModel())
            .hasFieldOrPropertyWithValue("productionYear", carBefore.getProductionYear())
            .hasFieldOrPropertyWithValue("pricePerDay", carBefore.getPricePerDay())
            .hasFieldOrPropertyWithValue("status", carBefore.getStatus());
  }

  @Test
  @DisplayName("Http PUT request to '/cars/{id}' updates the expected car.")
  void httpPut_to_updateCarMethod_updates_given_car() {
    // given
    Car carBefore = carTestHelper.createCar(false);
    CarReadModel carFromDb = carTestHelper.saveCarToRepository(carBefore);

    Car carAfter = carTestHelper.createCar(true);
    CarWriteModel carToUpdate = carTestHelper.createCarWriteModel(carAfter);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<CarWriteModel> request = new HttpEntity<>(carToUpdate, headers);

    // when
    restTemplate
            .put(URI.create("http://localhost:" + port + "/cars/" + carFromDb.getId()), request);

    // then
    assertThat(carRepository.findById(carFromDb.getId()).get())
            .isInstanceOf(Car.class)
            .hasFieldOrPropertyWithValue("make", carToUpdate.getMake())
            .hasFieldOrPropertyWithValue("model", carToUpdate.getModel())
            .hasFieldOrPropertyWithValue("productionYear", carToUpdate.getProductionYear())
            .hasFieldOrPropertyWithValue("pricePerDay", carToUpdate.getPricePerDay())
            .hasFieldOrPropertyWithValue("status", carToUpdate.getStatus());
  }
}