import { Component } from '@angular/core';
import { LoggingService } from './LoggingService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title: string;

  constructor(private loggingService: LoggingService) {
    this.title = this.getAppTitle();
    this.loggingService.logInfo('AppComponent initialized');
  }

  private getAppTitle(): string {
    return 'angularapp';
  }
}