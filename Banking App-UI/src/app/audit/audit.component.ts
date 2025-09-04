import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface AuditRecord {
  id: number;
  senderName: string;
  receiverName: string;
  amount: number;
  timestamp: string;
}

@Component({
  selector: 'app-audit',
  templateUrl: './audit.component.html',
  styleUrls: ['./audit.component.css']
})
export class AuditComponent implements OnInit {

  auditRecords: AuditRecord[] = [];
  loading: boolean = true;
  errorMessage: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchAuditRecords();
  }

  fetchAuditRecords(): void {
    this.http.get<AuditRecord[]>('http://localhost:8076/audit')  // Change URL if needed
      .subscribe({
        next: (data) => {
          this.auditRecords = data;
          this.loading = false;
        },
        error: (error) => {
          this.errorMessage = 'Failed to load audit records.';
          this.loading = false;
          console.error(error);
        }
      });
  }
}
