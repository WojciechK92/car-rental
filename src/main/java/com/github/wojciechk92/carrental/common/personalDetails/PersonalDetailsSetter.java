package com.github.wojciechk92.carrental.common.personalDetails;

import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.client.dto.ClientWriteModel;
import com.github.wojciechk92.carrental.employee.Employee;
import com.github.wojciechk92.carrental.employee.dto.EmployeeWriteModel;

public interface PersonalDetailsSetter {

  PersonalDetails setPersonalDetailsForClient(Client client, ClientWriteModel toUpdate);

  PersonalDetails setPersonalDetailsForEmployee(Employee employee, EmployeeWriteModel toUpdate);
}
