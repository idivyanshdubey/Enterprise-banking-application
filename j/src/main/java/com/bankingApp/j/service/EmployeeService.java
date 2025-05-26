package com.bankingApp.j.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import com.bankingApp.j.model.Employee;
import com.bankingApp.j.repository.EmployeeRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }
    
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);
        
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setGender(updatedEmployee.getGender());
        
        return employeeRepository.save(existingEmployee);
    }
    
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }
    
    public Map<String, Object> getEmployeeStats() {
        List<Employee> employees = employeeRepository.findAll();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEmployees", employees.size());
        
        // Average age
        double avgAge = employees.stream()
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0.0);
        stats.put("averageAge", avgAge);
        
        // Gender distribution
        Map<String, Long> genderDistribution = employees.stream()
                .collect(Collectors.groupingBy(
                    Employee::getGender, 
                    Collectors.counting()
                ));
        stats.put("genderDistribution", genderDistribution);
        
        // Age distribution
        Map<String, Long> ageDistribution = employees.stream()
                .collect(Collectors.groupingBy(
                    e -> {
                        if (e.getAge() < 20) return "Under 20";
                        else if (e.getAge() < 30) return "20-29";
                        else if (e.getAge() < 40) return "30-39";
                        else if (e.getAge() < 50) return "40-49";
                        else return "50+";
                    },
                    Collectors.counting()
                ));
        stats.put("ageDistribution", ageDistribution);
        
        return stats;
    }
    
    public ByteArrayResource exportEmployeesToCsv() throws IOException {
    try {
        List<Employee> employees = employeeRepository.findAll();
        
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("ID,Name,Age,Gender,Created At,Updated At\n");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        for (Employee employee : employees) {
            // Handle ID
            csvContent.append(employee.getId() != null ? employee.getId() : "").append(",");
            
            // Handle Name - escape commas and quotes
            String name = employee.getName();
            if (name != null) {
                // If name contains comma, wrap in quotes
                if (name.contains(",")) {
                    name = "\"" + name.replace("\"", "\"\"") + "\"";
                }
                csvContent.append(name);
            }
            csvContent.append(",");
            
            // Handle Age
            csvContent.append(employee.getAge() != null ? employee.getAge() : "").append(",");
            
            // Handle Gender
            csvContent.append(employee.getGender() != null ? employee.getGender() : "").append(",");
            
            // Handle Created At
            if (employee.getCreatedAt() != null) {
                csvContent.append(employee.getCreatedAt().format(formatter));
            }
            csvContent.append(",");
            
            // Handle Updated At
            if (employee.getUpdatedAt() != null) {
                csvContent.append(employee.getUpdatedAt().format(formatter));
            }
            csvContent.append("\n");
        }
        
        return new ByteArrayResource(csvContent.toString().getBytes("UTF-8"));
    } catch (Exception e) {
        System.err.println("Error generating CSV: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
}

    
    public ByteArrayResource exportEmployeesToExcel() throws IOException {
        List<Employee> employees = employeeRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Employees");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Age");
            headerRow.createCell(3).setCellValue("Gender");
            headerRow.createCell(4).setCellValue("Created At");
            headerRow.createCell(5).setCellValue("Updated At");
            
            // Create data rows
            int rowNum = 1;
            for (Employee employee : employees) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(employee.getId());
                row.createCell(1).setCellValue(employee.getName());
                row.createCell(2).setCellValue(employee.getAge());
                row.createCell(3).setCellValue(employee.getGender());
                
                if (employee.getCreatedAt() != null) {
                    row.createCell(4).setCellValue(employee.getCreatedAt().format(formatter));
                }
                
                if (employee.getUpdatedAt() != null) {
                    row.createCell(5).setCellValue(employee.getUpdatedAt().format(formatter));
                }
            }
            
            // Auto-size columns
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Write to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }
}
