package com.bankingApp.j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bankingApp.j.model.Employee;
import com.bankingApp.j.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    
    @Autowired
    private EmployeeService service;
    
    @GetMapping
    @Operation(summary = "Get All Employees", description = "Fetch a list of all employees.")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get Employee By ID", description = "Fetch an employee's details by providing their ID.")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = service.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Add New Employee", description = "Create a new employee record.")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        System.out.println("POST request received to create employee: " + employee);
        try {
            // For new employees, we should ignore any ID provided by the client
            // and let the database generate a new ID
            Employee newEmployee = new Employee(
                employee.getName(),
                employee.getAge(),
                employee.getGender()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createEmployee(newEmployee));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update Employee", description = "Update an existing employee's details by ID.")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = service.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Employee", description = "Delete an employee record by providing their ID.")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            service.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}