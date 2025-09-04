import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

interface Payment {
  userEmail: string;
  amount: number;
  status?: string;
  message?: string;
}

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  newPayment: Payment = {
    userEmail: '',
    amount: 0
  };

  paymentResponse: Payment | null = null;
  payments: Payment[] = [];

  private apiUrl = 'http://localhost:8073/api/payments'; 

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadPayments();
  }

  processPayment(): void {
    this.http.post<Payment>(this.apiUrl, this.newPayment).subscribe(response => {
      this.paymentResponse = response;
      this.loadPayments();
      this.resetForm();
    });
  }

  loadPayments(): void {
    this.http.get<Payment[]>(this.apiUrl).subscribe(data => {
      this.payments = data;
    });
  }

  resetForm(): void {
    this.newPayment = {
      userEmail: '',
      amount: 0
    };
  }
}

