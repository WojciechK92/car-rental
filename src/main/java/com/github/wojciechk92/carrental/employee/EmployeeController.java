package com.github.wojciechk92.carrental.employee;

import com.github.wojciechk92.carrental.employee.dto.EmployeeReadModel;
import com.github.wojciechk92.carrental.employee.dto.EmployeeWriteModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping
  ResponseEntity<List<EmployeeReadModel>> getEmployees(Pageable pageable) {
    return ResponseEntity.ok(employeeService.getEmployees(pageable));
  }

  @GetMapping("/{id}")
  ResponseEntity<EmployeeReadModel> getEmployee(@PathVariable Long id) {
    return ResponseEntity.ok(employeeService.getEmployee(id));
  }

  @PostMapping
  ResponseEntity<EmployeeReadModel> createEmployee(@RequestBody @Valid EmployeeWriteModel employee) {
    EmployeeReadModel result = employeeService.createEmployee(employee);
    return ResponseEntity.created(URI.create("/employees/" + result.getId())).body(result);
  }

}
