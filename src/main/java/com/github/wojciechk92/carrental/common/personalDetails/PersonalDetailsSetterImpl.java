package com.github.wojciechk92.carrental.common.personalDetails;

import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.client.dto.ClientWriteModel;
import com.github.wojciechk92.carrental.employee.Employee;
import com.github.wojciechk92.carrental.employee.dto.EmployeeWriteModel;
import org.springframework.stereotype.Component;

@Component
public class PersonalDetailsSetterImpl implements PersonalDetailsSetter {
  @Override
  public PersonalDetails setPersonalDetailsForClient(Client client, ClientWriteModel toUpdate) {
    return setDetails(client.getPersonalDetails(), toUpdate.getFirstName(), toUpdate.getLastName(), toUpdate.getEmail(), toUpdate.getTel());
  }

  @Override
  public PersonalDetails setPersonalDetailsForEmployee(Employee employee, EmployeeWriteModel toUpdate) {
    return setDetails(employee.getPersonalDetails(), toUpdate.getFirstName(), toUpdate.getLastName(), toUpdate.getEmail(), toUpdate.getTel());
  }

  private PersonalDetails setDetails(PersonalDetails details, String firstName, String lastName, String email, int tel) {
    details.setFirstName(firstName);
    details.setLastName(lastName);
    details.setEmail(email);
    details.setTel(tel);

    return details;
  }
}
