import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

interface Customer {
  customerId?: number;
  name: string;
  email: string;
  mobileNumber: string;
  address: string;
  age: number;
  gender: string;
}

@Component({
  selector: 'app-customer',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {
  customers: Customer[] = [];

  newCustomer: Customer = {
    name: '',
    email: '',
    mobileNumber: '',
    address: '',
    age: 0,
    gender: ''
  };

  private apiUrl = 'http://localhost:8070/api/customer';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers(): void {
    this.http.get<Customer[]>(this.apiUrl).subscribe(data => {
      this.customers = data;
    });
  }

  saveCustomer(): void {
    console.log('Save button clicked');
    this.http.post<Customer>(this.apiUrl, this.newCustomer).subscribe(() => {
      this.loadCustomers();
      this.resetForm();
    });
  }

  resetForm(): void {
    this.newCustomer = {
      name: '',
      email: '',
      mobileNumber: '',
      address: '',
      age: 0,
      gender: ''
    };
  }
}
