package com.bankingApp.j.service;

import org.springframework.stereotype.Service;

import com.bankingApp.j.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    private final HashMap<Long, Employee> employeeMap = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    public Employee getEmployeeById(Long id) {
        return employeeMap.get(id);
    }

    public Employee createEmployee(Employee employee) {
        Long id = idGenerator.getAndIncrement();
        employee.setId(id);
        employeeMap.put(id, employee);
        return employee;
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeMap.get(id);
        if (existingEmployee != null) {
            updatedEmployee.setId(id);
            employeeMap.put(id, updatedEmployee);
            return updatedEmployee;
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        employeeMap.remove(id);
    }
}
