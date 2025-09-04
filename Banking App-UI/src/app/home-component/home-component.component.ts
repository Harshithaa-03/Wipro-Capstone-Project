import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MaterialModule } from '../material.module';

@Component({
  selector: 'app-home-component',
  imports: [RouterLink,CommonModule,MaterialModule],
  templateUrl: './home-component.component.html',
  styleUrls: ['./home-component.component.css']
})
export class HomeComponent {
  isUserLoggedIn = false;
  isAdminLoggedIn = false;

  
  loginUser() {
    this.isUserLoggedIn = true;
    this.isAdminLoggedIn = false;
  }

  
  loginAdmin() {
    this.isAdminLoggedIn = true;
    this.isUserLoggedIn = false;
  }

  
  logout() {
    this.isUserLoggedIn = false;
    this.isAdminLoggedIn = false;
  }
}

