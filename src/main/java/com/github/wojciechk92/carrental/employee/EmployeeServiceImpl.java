package com.github.wojciechk92.carrental.employee;

import com.github.wojciechk92.carrental.employee.dto.EmployeeReadModel;
import com.github.wojciechk92.carrental.employee.dto.EmployeeWriteModel;
import com.github.wojciechk92.carrental.employee.exception.EmployeeException;
import com.github.wojciechk92.carrental.employee.exception.EmployeeExceptionMessage;
import com.github.wojciechk92.carrental.employee.validator.EmployeeValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final EmployeeValidator validator;

  @Autowired
  EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeValidator validator) {
    this.employeeRepository = employeeRepository;
    this.validator = validator;
  }

  @Override
  public List<EmployeeReadModel> getEmployees(Pageable pageable) {
    return employeeRepository.findAll(pageable).getContent().stream()
            .map(EmployeeReadModel::new)
            .toList();
  }

  @Override
  public EmployeeReadModel getEmployee(Long id) {
    return employeeRepository.findById(id)
            .map(EmployeeReadModel::new)
            .orElseThrow(() -> new EmployeeException(EmployeeExceptionMessage.EMPLOYEE_NOT_FOUND));
  }

  @Override
  public EmployeeReadModel createEmployee(EmployeeWriteModel toSave) {
    boolean uniqueEmail = validator.validateUniqueEmail(toSave.getEmail());
    boolean uniqueTel = validator.validateUniqueTel(toSave.getTel());

    if (!uniqueEmail) throw new EmployeeException(EmployeeExceptionMessage.EMAIL_IS_NOT_UNIQUE);
    if (!uniqueTel) throw new EmployeeException(EmployeeExceptionMessage.TEL_IS_NOT_UNIQUE);

    Employee result = employeeRepository.save(toSave.toEmployee());
    return new EmployeeReadModel(result);
  }

  @Transactional
  @Override
  public void updateEmployee(EmployeeWriteModel toUpdate, Long id) {
    boolean uniqueEmail = validator.validateUniqueEmail(toUpdate.getEmail());
    boolean uniqueTel = validator.validateUniqueTel(toUpdate.getTel());

    employeeRepository.findById(id)
            .map(employee -> {
              if (!uniqueEmail && !employee.getPersonalDetails().getEmail().equals(toUpdate.getEmail())) throw new EmployeeException(EmployeeExceptionMessage.EMAIL_IS_NOT_UNIQUE);
              if (!uniqueTel && employee.getPersonalDetails().getTel() != toUpdate.getTel()) throw new EmployeeException(EmployeeExceptionMessage.TEL_IS_NOT_UNIQUE);
              employee.getPersonalDetails().setFirstName(toUpdate.getFirstName());
              employee.getPersonalDetails().setLastName(toUpdate.getLastName());
              employee.getPersonalDetails().setEmail(toUpdate.getEmail());
              employee.getPersonalDetails().setTel(toUpdate.getTel());
              employee.setStatus(toUpdate.getStatus());
              return employee;
            })
            .orElseThrow(() -> new EmployeeException(EmployeeExceptionMessage.EMPLOYEE_NOT_FOUND));
  }

  @Transactional
  @Override
  public void setStatusTo(EmployeeStatus status, Long id) {

    employeeRepository.findById(id)
            .map(employee -> {
              employee.setStatus(status);
              return employee;
            })
            .orElseThrow(() -> new EmployeeException(EmployeeExceptionMessage.EMPLOYEE_NOT_FOUND));
  }
}
