import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../models/Employee';

@Injectable({
  providedIn: 'root'
})

export class EmployeeService {

  backendUrl = "http://localhost:8080/api/employees";

  constructor(private http:HttpClient) { }

  
  // GET: Fetch all employees
  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.backendUrl);
  }

  // POST: Add a new employee
  addEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.backendUrl, employee);
  }

  // PUT: Update an existing employee
  updateEmployee(employeeId: number, employee: Employee): Observable<Employee> {
    const url = `${this.backendUrl}/${employeeId}`;
    return this.http.put<Employee>(url, employee);
  }

  // DELETE: Remove an employee by ID
  deleteEmployee(employeeId: number): Observable<void> {
    const url = `${this.backendUrl}/${employeeId}`;
    return this.http.delete<void>(url);
  }
}