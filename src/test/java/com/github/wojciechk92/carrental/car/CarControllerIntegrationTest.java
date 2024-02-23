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
    CarReadModel car= createAndSaveCarToRepository(true);

    String carAsString = mapObjectToString(car);

    mockMvc.perform(MockMvcRequestBuilders.get("/cars/" + car.getId()))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string(carAsString));
  }

  @Test
  @DisplayName("Http GET request to '/cars/{id}' returns the exception message.")
  void httpGet_toGetCarMethod_returns_exception_message() throws Exception {
    CarReadModel car = createAndSaveCarToRepository(true);

    ExceptionMessage message = createExceptionMessage("carId", CarExceptionMessage.CAR_NOT_FOUND);

    String messageAsString = mapObjectToString(message);

    mockMvc.perform(MockMvcRequestBuilders.get("/cars/" + car.getId() + 1))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string(messageAsString));
  }

  @Test
  @DisplayName("Http POST request to '/cars' creates and returns new car.")
  void httpPost_createCar_method_returns_new_car() throws Exception {
    CarWriteModel carToSave = createCarWriteModel(true);

    CarReadModel carToReturn = createCarReadModel(true);
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

  private Car createCar(boolean yearIsCorrect) {
    return new Car("audi", "a8", yearIsCorrect ? 2018 : 2035, 789.79, CarStatus.AVAILABLE);
  }

  private CarReadModel createAndSaveCarToRepository(boolean yearIsCorrect) {
    Car carBefore = createCar(yearIsCorrect);
    Car carAfter = carRepository.save(carBefore);
    return new CarReadModel(carAfter);
  }

  private CarWriteModel createCarWriteModel(boolean yearIsCorrect){
    Car carBefore = createCar(yearIsCorrect);
    CarWriteModel carAfter = new CarWriteModel();
    carAfter.setMake(carBefore.getMake());
    carAfter.setModel(carBefore.getModel());
    carAfter.setProductionYear(carBefore.getProductionYear());
    carAfter.setPricePerDay(carBefore.getPricePerDay());
    carAfter.setStatus(carBefore.getStatus());

    return carAfter;
  }

  private CarReadModel createCarReadModel(boolean yearIsCorrect){
    Car carBefore = createCar(yearIsCorrect);
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