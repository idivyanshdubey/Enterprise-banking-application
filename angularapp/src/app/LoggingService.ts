import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoggingService {
  private apiUrl = 'http://localhost:8080/api/employees'; // Update if your backend logging endpoint is different

  constructor(private http: HttpClient) {}

  logInfo(message: string) {
    console.log(`Info: ${message}`);
    this.sendLog('info', message);
  }

  logWarning(message: string) {
    console.warn(`Warning: ${message}`);
    this.sendLog('warning', message);
  }

  logError(message: string) {
    console.error(`Error: ${message}`);
    this.sendLog('error', message);
  }

  private sendLog(level: string, message: string) {
    const logEntry = {
      level,
      message,
      timestamp: new Date().toISOString()
    };
    this.http.post(this.apiUrl, logEntry).subscribe({
      next: () => {},
      error: err => {
        // Optionally handle errors here
        console.error('Failed to send log to backend', err);
      }
    });
  }
}