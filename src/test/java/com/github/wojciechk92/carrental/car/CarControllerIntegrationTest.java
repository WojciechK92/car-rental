package com.github.wojciechk92.carrental.car;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

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
  CarRepository carRepository;

  @AfterEach
  void cleanTable() {
    ((CarRepositorySqlAdapter) carRepository).deleteAll();
    entityManager.createNativeQuery("ALTER TABLE cars AUTO_INCREMENT = 1").executeUpdate();
  }

  @Test
  @DisplayName("Http GET request to '/cars/{id}' returns the expected car.")
  void httpGet_toGetCarMethod_returns_given_car() throws Exception {
    Car carBefore = createCar(true, false);
    CarReadModel carAfter= saveCarToRepository(carBefore);

    String carAfterAsString = mapObjectToString(carAfter);

    mockMvc.perform(MockMvcRequestBuilders.get("/cars/" + carAfter.getId()))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string(carAfterAsString));
  }

  @Test
  @DisplayName("Http GET request to '/cars/{id}' returns the exception message.")
  void httpGet_toGetCarMethod_returns_exception_message() throws Exception {
    Car carBefore = createCar(true, false);
    CarReadModel carAfter = saveCarToRepository(carBefore);

    ExceptionMessage message = createExceptionMessage("carId", CarExceptionMessage.CAR_NOT_FOUND);

    String messageAsString = mapObjectToString(message);

    mockMvc.perform(MockMvcRequestBuilders.get("/cars/" + carAfter.getId() + 1))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string(messageAsString));
  }

  @Test
  @DisplayName("Http POST request to '/cars' creates and returns new car.")
  void httpPost_createCar_method_returns_new_car() throws Exception {
    Car carBefore = createCar(true, false);
    CarWriteModel carToSave = createCarWriteModel(carBefore);

    CarReadModel carToReturn = createCarReadModel(carBefore);
    carToReturn.setId(1L);
    String carToReturnAsString = mapObjectToString(carToReturn);

    mockMvc.perform(MockMvcRequestBuilders
                    .post("/cars")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .content(mapObjectToString(carToSave)))
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
  void httpPut_updateCar_method_updates_given_car() throws Exception {
    Car carBefore = createCar(true, false);
    CarReadModel carFromDb = saveCarToRepository(carBefore);

    Car carAfter = createCar(true, true);
    CarWriteModel carToUpdate = createCarWriteModel(carAfter);
    String carToUpdateAsString = mapObjectToString(carToUpdate);

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
  void httpPut_setStatusTo_method_updates_status_for_given_car() throws Exception {
    Car carBefore = createCar(true, false);
    CarReadModel carFromDb = saveCarToRepository(carBefore);

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

  private Car createCar(boolean yearIsCorrect, boolean secondVersion) {
    if (secondVersion) return new Car("toyota", "avensis", yearIsCorrect ? 2023 : 2035, 559.79, CarStatus.INACTIVE);
    return new Car("audi", "a8", yearIsCorrect ? 2018 : 2035, 789.79, CarStatus.AVAILABLE);
  }

  private CarReadModel saveCarToRepository(Car carBefore) {
    Car carAfter = carRepository.save(carBefore);
    return new CarReadModel(carAfter);
  }

  private CarWriteModel createCarWriteModel(Car carBefore){
    CarWriteModel carAfter = new CarWriteModel();
    carAfter.setMake(carBefore.getMake());
    carAfter.setModel(carBefore.getModel());
    carAfter.setProductionYear(carBefore.getProductionYear());
    carAfter.setPricePerDay(carBefore.getPricePerDay());
    carAfter.setStatus(carBefore.getStatus());

    return carAfter;
  }

  private CarReadModel createCarReadModel(Car carBefore){
    CarReadModel carAfter = new CarReadModel();
    carAfter.setMake(carBefore.getMake());
    carAfter.setModel(carBefore.getModel());
    carAfter.setProductionYear(carBefore.getProductionYear());
    carAfter.setPricePerDay(carBefore.getPricePerDay());
    carAfter.setStatus(carBefore.getStatus());
    carAfter.setRentalIdList(List.of());

    return carAfter;
  }

  private String mapObjectToString(Object toMap) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(toMap);
  }

  private ExceptionMessage createExceptionMessage(String fieldName, CarExceptionMessage exceptionMessage) {
    ExceptionMessage message = new ExceptionMessage();
    message.addError(fieldName, exceptionMessage.getMessage());
    return message;
  }
}