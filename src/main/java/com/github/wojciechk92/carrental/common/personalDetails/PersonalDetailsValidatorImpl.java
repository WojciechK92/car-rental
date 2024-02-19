package com.github.wojciechk92.carrental.common.personalDetails;

import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.client.ClientRepository;
import com.github.wojciechk92.carrental.client.dto.ClientWriteModel;
import com.github.wojciechk92.carrental.employee.Employee;
import com.github.wojciechk92.carrental.employee.EmployeeRepository;
import com.github.wojciechk92.carrental.employee.dto.EmployeeWriteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonalDetailsValidatorImpl implements PersonalDetailsValidator {
  private final ClientRepository clientRepository;
  private final EmployeeRepository employeeRepository;

  @Autowired
  public PersonalDetailsValidatorImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository) {
    this.clientRepository = clientRepository;
    this.employeeRepository = employeeRepository;
  }

  @Override
  public void validateOnSaveForClient(ClientWriteModel client) {
    boolean uniqueEmail = validateUniqueEmailForClient(client.getEmail());
    boolean uniqueTel = validateUniqueTelForClient(client.getTel());
    
    validateConditionsOnSave(uniqueEmail, uniqueTel);
  }

  @Override
  public void validateOnSaveForEmployee(EmployeeWriteModel employee) {
    boolean uniqueEmail = validateUniqueEmailForEmployee(employee.getEmail());
    boolean uniqueTel = validateUniqueTelForEmployee(employee.getTel());

    validateConditionsOnSave(uniqueEmail, uniqueTel);
  }

  @Override
  public void validateOnUpdateForClient(Client previousClient, ClientWriteModel nextClient) {
    boolean uniqueEmail = validateUniqueEmailForClient(nextClient.getEmail());
    boolean uniqueTel = validateUniqueTelForClient(nextClient.getTel());
    boolean equalEmails = previousClient.getPersonalDetails().getEmail().equals(nextClient.getEmail());
    boolean equalTels = previousClient.getPersonalDetails().getTel() == nextClient.getTel();

    validateConditionsOnUpdate(uniqueEmail, uniqueTel, equalEmails, equalTels);
  }

  @Override
  public void validateOnUpdateForEmployee(Employee previousEmployee, EmployeeWriteModel nextEmployee) {
    boolean uniqueEmail = validateUniqueEmailForEmployee(nextEmployee.getEmail());
    boolean uniqueTel = validateUniqueTelForEmployee(nextEmployee.getTel());
    boolean equalEmails = previousEmployee.getPersonalDetails().getEmail().equals(nextEmployee.getEmail());
    boolean equalTels = previousEmployee.getPersonalDetails().getTel() == nextEmployee.getTel();

    validateConditionsOnUpdate(uniqueEmail, uniqueTel, equalEmails, equalTels);
  }

  private boolean validateUniqueEmailForClient(String email) {
    return !clientRepository.existsByPersonalDetailsEmail(email);
  }

  private boolean validateUniqueTelForClient(int tel) {
    return !clientRepository.existsByPersonalDetailsTel(tel);
  }
  
  private boolean validateUniqueEmailForEmployee(String email) {
    return !employeeRepository.existsByPersonalDetailsEmail(email);
  }
  
  private boolean validateUniqueTelForEmployee(int tel) {
    return !employeeRepository.existsByPersonalDetailsTel(tel);
  }

  private void validateConditionsOnSave(boolean uniqueEmail, boolean uniqueTel) {
    if (!uniqueEmail) throw new PersonalDetailsException(PersonalDetailsExceptionMessage.EMAIL_IS_NOT_UNIQUE);
    if (!uniqueTel) throw new PersonalDetailsException(PersonalDetailsExceptionMessage.TEL_IS_NOT_UNIQUE);
  }

  private void validateConditionsOnUpdate(boolean uniqueEmail, boolean uniqueTel, boolean equalEmails, boolean equalTels) {
    if (!uniqueEmail && !equalEmails) throw new PersonalDetailsException(PersonalDetailsExceptionMessage.EMAIL_IS_NOT_UNIQUE);
    if (!uniqueTel && !equalTels) throw new PersonalDetailsException(PersonalDetailsExceptionMessage.TEL_IS_NOT_UNIQUE);
  }
}
