package com.github.wojciechk92.carrental.employee;

import com.github.wojciechk92.carrental.employee.dto.EmployeeReadModel;
import com.github.wojciechk92.carrental.employee.dto.EmployeeWriteModel;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("employees")
@EnableMethodSecurity(jsr250Enabled = true)
public class EmployeeController {
  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping
  @RolesAllowed("ADMIN")
  ResponseEntity<List<EmployeeReadModel>> getEmployees(Pageable pageable) {
    return ResponseEntity.ok(employeeService.getEmployees(pageable));
  }

  @GetMapping("/{id}")
  @RolesAllowed("ADMIN")
  ResponseEntity<EmployeeReadModel> getEmployee(@PathVariable Long id) {
    return ResponseEntity.ok(employeeService.getEmployee(id));
  }

  @PostMapping
  @RolesAllowed("ADMIN")
  ResponseEntity<EmployeeReadModel> createEmployee(@RequestBody @Valid EmployeeWriteModel employee) {
    EmployeeReadModel result = employeeService.createEmployee(employee);
    return ResponseEntity.created(URI.create("/employees/" + result.getId())).body(result);
  }

  @PutMapping("/{id}")
  @RolesAllowed("ADMIN")
  public ResponseEntity<Void> updateEmployee(@RequestBody @Valid EmployeeWriteModel employee, @PathVariable Long id) {
    employeeService.updateEmployee(employee, id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}")
  @RolesAllowed("ADMIN")
  public ResponseEntity<Void> setStatusTo(@RequestParam EmployeeStatus status, @PathVariable Long id) {
    employeeService.setStatusTo(status, id);
    return ResponseEntity.noContent().build();
  }

}
