package com.github.wojciechk92.carrental.employee;

import com.github.wojciechk92.carrental.employee.dto.EmployeeReadModel;
import com.github.wojciechk92.carrental.employee.dto.EmployeeWriteModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

 List<EmployeeReadModel> getEmployees(Pageable pageable);

 EmployeeReadModel getEmployee(Long id);

 EmployeeReadModel createEmployee(EmployeeWriteModel employee);

 void updateEmployee(EmployeeWriteModel employee, Long id);

 void setStatusTo(EmployeeStatus status, Long id);

}
