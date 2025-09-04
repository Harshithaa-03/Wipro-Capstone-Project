import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

interface Notification {
  id?: number;         
  userEmail: string;
  amount: number;
  status: string;      
  message: string;
}

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {
  notifications: Notification[] = [];

  newNotification: Notification = {
    userEmail: '',
    amount: 0,
    status: 'SUCCESS',
    message: ''
  };

  private apiUrl = 'http://localhost:8072/api/notifications';  

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadNotifications();
  }

  loadNotifications(): void {
    this.http.get<Notification[]>(this.apiUrl).subscribe(data => {
      this.notifications = data;
    });
  }

  saveNotification(): void {
    this.http.post<Notification>(this.apiUrl, this.newNotification).subscribe(() => {
      this.loadNotifications();
      this.resetForm();
    });
  }

  resetForm(): void {
    this.newNotification = {
      userEmail: '',
      amount: 0,
      status: 'SUCCESS',
      message: ''
    };
  }
}

