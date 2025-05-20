import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Employee } from '../../models/Employee';
import { EmployeeService } from '../../service/employee.service';
import { CommonModule } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css'], 
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule]
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];
  employeeForm: FormGroup;
  isEditing = false;
  currentEmployeeId: number | null = null;
  showForm = false;
  
  constructor(
    private employeeService: EmployeeService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) {
    this.employeeForm = this.fb.group({
      name: ['', [Validators.required]],
      age: ['', [Validators.required, Validators.min(18), Validators.max(100)]],
      gender: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.getEmployees();
  }

  getEmployees() {
    this.employeeService.getEmployees().subscribe({
      next: (data) => {
        this.employees = data;
      },
      error: (error) => {
        console.error('Error fetching employees:', error);
        this.showNotification('Failed to load employees. Please try again later.', 'Close');
      }
    });
  }

  openAddForm() {
    this.isEditing = false;
    this.currentEmployeeId = null;
    this.employeeForm.reset();
    this.showForm = true;
  }

  openEditForm(employee: Employee) {
    this.isEditing = true;
    this.currentEmployeeId = Number(employee.id);
    this.employeeForm.setValue({
      name: employee.name,
      age: employee.age,
      gender: employee.gender
    });
    this.showForm = true;
  }

  cancelForm() {
    this.showForm = false;
    this.employeeForm.reset();
  }

  submitForm() {
    if (this.employeeForm.invalid) {
      this.employeeForm.markAllAsTouched();
      return;
    }

    const employeeData: Employee = this.employeeForm.value;

    if (this.isEditing && this.currentEmployeeId) {
      this.employeeService.updateEmployee(this.currentEmployeeId, employeeData).subscribe({
        next: () => {
          this.getEmployees();
          this.showForm = false;
          this.employeeForm.reset();
          this.showNotification('Employee updated successfully!', 'Close');
        },
        error: (error) => {
          console.error('Error updating employee:', error);
          this.showNotification('Failed to update employee. Please try again.', 'Close');
        }
      });
    } else {
      this.employeeService.addEmployee(employeeData).subscribe({
        next: () => {
          this.getEmployees();
          this.showForm = false;
          this.employeeForm.reset();
          this.showNotification('Employee added successfully!', 'Close');
        },
        error: (error) => {
          console.error('Error adding employee:', error);
          this.showNotification('Failed to add employee. Please try again.', 'Close');
        }
      });
    }
  }

  deleteEmployee(id: number) {
    if (confirm('Are you sure you want to delete this employee?')) {
      this.employeeService.deleteEmployee(id).subscribe({
        next: () => {
          this.getEmployees();
          this.showNotification('Employee deleted successfully!', 'Close');
        },
        error: (error) => {
          console.error('Error deleting employee:', error);
          this.showNotification('Failed to delete employee. Please try again.', 'Close');
        }
      });
    }
  }

  showNotification(message: string, action: string = 'OK') {
    this.snackBar.open(message, action, {
      duration: 3000,
      verticalPosition: 'top',
      horizontalPosition: 'right',
      panelClass: ['custom-snackbar']
    });
  }
}