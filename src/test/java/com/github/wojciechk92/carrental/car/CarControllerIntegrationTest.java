package com.github.wojciechk92.carrental.car;

import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.car.dto.CarWriteModel;
import com.github.wojciechk92.carrental.car.exception.CarExceptionMessage;
import com.github.wojciechk92.carrental.common.dto.ExceptionMessage;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CarControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private CarRepository carRepository;
  @Autowired
  private CarTestHelper carTestHelper;

  @AfterEach
  void cleanTable() {
    ((CarRepositorySqlAdapter) carRepository).deleteAll();
    entityManager.createNativeQuery("ALTER TABLE cars AUTO_INCREMENT = 1").executeUpdate();
  }

  @Test
  @DisplayName("Http GET request to '/cars/{id}' returns the expected car.")
  void httpGet_to_getCarMethod_returns_given_car() throws Exception {
    Car carBefore = carTestHelper.createCar(false);
    CarReadModel carAfter= carTestHelper.saveCarToRepository(carBefore);

    String carAfterAsString = carTestHelper.mapObjectToString(carAfter);

    mockMvc.perform(MockMvcRequestBuilders.get("/cars/" + carAfter.getId()))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string(carAfterAsString));
  }

  @Test
  @DisplayName("Http GET request to '/cars/{id}' returns the exception message.")
  void httpGet_to_getCarMethod_returns_exception_message() throws Exception {
    Car carBefore = carTestHelper.createCar(false);
    CarReadModel carAfter = carTestHelper.saveCarToRepository(carBefore);

    ExceptionMessage message = carTestHelper.createExceptionMessage("carId", CarExceptionMessage.CAR_NOT_FOUND);

    String messageAsString = carTestHelper.mapObjectToString(message);

    mockMvc.perform(MockMvcRequestBuilders.get("/cars/" + carAfter.getId() + 1))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string(messageAsString));
  }

  @Test
  @DisplayName("Http POST request to '/cars' creates and returns new car.")
  void httpPost_to_createCarMethod_returns_new_car() throws Exception {
    Car carBefore = carTestHelper.createCar(false);
    CarWriteModel carToSave = carTestHelper.createCarWriteModel(carBefore);

    CarReadModel carToReturn = carTestHelper.createCarReadModel(carBefore);
    carToReturn.setId(1L);
    String carToReturnAsString = carTestHelper.mapObjectToString(carToReturn);

    mockMvc.perform(MockMvcRequestBuilders
                    .post("/cars")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .content(carTestHelper.mapObjectToString(carToSave)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.make", Is.is(carToReturn.getMake())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is(carToReturn.getModel())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.productionYear", Is.is(carToReturn.getProductionYear())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pricePerDay", Is.is(carToReturn.getPricePerDay())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", equalToIgnoringCase(carToReturn.getStatus().toString())));

    assertThat(carRepository.findById(carToReturn.getId()).get())
            .isInstanceOf(Car.class)
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("make", carToReturn.getMake())
            .hasFieldOrPropertyWithValue("model", carToReturn.getModel())
            .hasFieldOrPropertyWithValue("productionYear", carToReturn.getProductionYear())
            .hasFieldOrPropertyWithValue("pricePerDay", carToReturn.getPricePerDay())
            .hasFieldOrPropertyWithValue("status", carToReturn.getStatus());

  }

  @Test
  @DisplayName("Http PUT method to '/cars/{id}' updates given car.")
  void httpPut_to_updateCarMethod_updates_given_car() throws Exception {
    Car carBefore = carTestHelper.createCar(false);
    CarReadModel carFromDb = carTestHelper.saveCarToRepository(carBefore);

    Car carAfter = carTestHelper.createCar(true);
    CarWriteModel carToUpdate = carTestHelper.createCarWriteModel(carAfter);
    String carToUpdateAsString = carTestHelper.mapObjectToString(carToUpdate);

    mockMvc.perform(MockMvcRequestBuilders
              .put("/cars/" + 1)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .content(carToUpdateAsString))
            .andExpect(MockMvcResultMatchers.status().isNoContent());

    assertThat(carRepository.findById(carFromDb.getId()).get())
            .isInstanceOf(Car.class)
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("make", carToUpdate.getMake())
            .hasFieldOrPropertyWithValue("model", carToUpdate.getModel())
            .hasFieldOrPropertyWithValue("productionYear", carToUpdate.getProductionYear())
            .hasFieldOrPropertyWithValue("pricePerDay", carToUpdate.getPricePerDay())
            .hasFieldOrPropertyWithValue("status", carToUpdate.getStatus());
  }

  @Test
  @DisplayName("Http PATCH method to '/cars/{id}' updates status for given car.")
  void httpPut_to_setStatusToMethod_updates_status_for_given_car() throws Exception {
    Car carBefore = carTestHelper.createCar(false);
    CarReadModel carFromDb = carTestHelper.saveCarToRepository(carBefore);

    mockMvc.perform(MockMvcRequestBuilders
                    .patch("/cars/" + 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("status", "IN_SERVICE"))
            .andExpect(MockMvcResultMatchers.status().isNoContent());

    assertThat(carRepository.findById(carFromDb.getId()).get())
            .isInstanceOf(Car.class)
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("status", CarStatus.IN_SERVICE);
  }
}