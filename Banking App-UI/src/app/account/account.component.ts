import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

interface Account {
  id?: number;
  customerId: number;
  userName: string;
  panCardNumber: string;
  aadharCardNumber: string;
  bankAccountNumber: string;
  accountType: string;
  password: string;
  balance: number;
  loan: number;
  address: string;
}

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  accounts: Account[] = [];

  newAccount: Account = {
    customerId: 0,
    userName: '',
    panCardNumber: '',
    aadharCardNumber: '',
    bankAccountNumber: '',
    accountType: '',
    password: '',
    balance: 0,
    loan: 0,
    address: ''
  };

  private apiUrl = 'http://localhost:8071/api/accounts'; 

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadAccounts();
  }

  loadAccounts(): void {
    this.http.get<Account[]>(this.apiUrl).subscribe(data => {
      this.accounts = data;
    });
  }

  saveAccount(): void {
    this.http.post<Account>(this.apiUrl, this.newAccount).subscribe(() => {
      this.loadAccounts();
      this.resetForm();
    });
  }

  resetForm(): void {
    this.newAccount = {
      customerId: 0,
      userName: '',
      panCardNumber: '',
      aadharCardNumber: '',
      bankAccountNumber: '',
      accountType: '',
      password: '',
      balance: 0,
      loan: 0,
      address: ''
    };
  }
}
