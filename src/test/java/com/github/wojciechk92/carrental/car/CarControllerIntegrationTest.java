package com.github.wojciechk92.carrental.car;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wojciechk92.carrental.car.dto.CarReadModel;
import com.github.wojciechk92.carrental.car.exception.CarExceptionMessage;
import com.github.wojciechk92.carrental.common.dto.ExceptionMessage;
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


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CarControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  CarRepository carRepository;

  @AfterEach
  void cleanTable() {
    ((CarRepositorySqlAdapter) carRepository).deleteAll();
  }

  @Test
  @DisplayName("Http GET request to '/cars/{id}' returns the expected car.")
  void httpGet_toGetCarMethod_returns_given_car() throws Exception {
    CarReadModel car= createAndSaveCarToRepository();

    String carAsString = mapObjectToString(car);

    mockMvc.perform(MockMvcRequestBuilders.get("/cars/" + car.getId()))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string(carAsString));
  }

  @Test
  @DisplayName("Http GET request to '/cars/{id}' returns the exception message.")
  void httpGet_toGetCarMethod_returns_exception_message() throws Exception {
    CarReadModel car = createAndSaveCarToRepository();

    ExceptionMessage message = createExceptionMessage("carId", CarExceptionMessage.CAR_NOT_FOUND);

    String messageAsString = mapObjectToString(message);

    mockMvc.perform(MockMvcRequestBuilders.get("/cars/" + car.getId() + 1))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string(messageAsString));
  }

  private CarReadModel createAndSaveCarToRepository() {
    Car carBefore = new Car("audi", "a8", 2018, 789.79, CarStatus.AVAILABLE);
    Car carAfter = carRepository.save(carBefore);
    return new CarReadModel(carAfter);
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