package com.github.wojciechk92.carrental.common.personalDetails;

import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.client.dto.ClientWriteModel;
import com.github.wojciechk92.carrental.employee.Employee;
import com.github.wojciechk92.carrental.employee.dto.EmployeeWriteModel;

public interface PersonalDetailsValidator {

  void validateOnSaveForClient(ClientWriteModel client);
  
  void validateOnSaveForEmployee(EmployeeWriteModel employee);

  void validateOnUpdateForClient(Client previousClient, ClientWriteModel nextClient);
  
  void validateOnUpdateForEmployee(Employee previousEmployee, EmployeeWriteModel nextEmployee);

}
