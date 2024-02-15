package com.github.wojciechk92.carrental.employee;

import com.github.wojciechk92.carrental.employee.dto.EmployeeReadModel;
import com.github.wojciechk92.carrental.employee.dto.EmployeeWriteModel;
import com.github.wojciechk92.carrental.employee.exception.EmployeeException;
import com.github.wojciechk92.carrental.employee.exception.EmployeeExceptionMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeRepository employeeRepository;

  @Autowired
  EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
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
    Employee result = employeeRepository.save(toSave.toEmployee());
    return new EmployeeReadModel(result);
  }

  @Transactional
  @Override
  public void updateEmployee(EmployeeWriteModel toUpdate, Long id) {
    employeeRepository.findById(id)
            .map(employee -> {
              employee.setFirstName(toUpdate.getFirstName());
              employee.setLastName(toUpdate.getLastName());
              employee.setEmail(toUpdate.getEmail());
              employee.setTel(toUpdate.getTel());
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
