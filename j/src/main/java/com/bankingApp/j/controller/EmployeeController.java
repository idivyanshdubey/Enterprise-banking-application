package com.bankingApp.j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bankingApp.j.model.Employee;
import com.bankingApp.j.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")  // Enable CORS for all origins
public class EmployeeController {
    
    @Autowired
    private EmployeeService service;

    /**
     * Get all employees
     */
    @GetMapping
    @Operation(summary = "Get All Employees", description = "Fetch a list of all employees.")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    /**
     * Get an employee by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Employee By ID", description = "Fetch an employee's details by providing their ID.")
    public Employee getEmployeeById(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }

    /**
     * Add a new employee
     */
    @PostMapping
    @Operation(summary = "Add New Employee", description = "Create a new employee record.")
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.createEmployee(employee);
    }

    /**
     * Update an employee's details
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update Employee", description = "Update an existing employee's details by ID.")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return service.updateEmployee(id, employee);
    }

    /**
     * Delete an employee by ID
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Employee", description = "Delete an employee record by providing their ID.")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
}
