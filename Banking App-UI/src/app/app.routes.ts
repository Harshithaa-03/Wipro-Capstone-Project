import { Routes } from '@angular/router';
import { LoginComponent } from './auth/components/login/login.component';
import { SignupComponent } from './auth/components/signup/signup.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CustomerComponent } from './customer/customer.component';
import { AccountComponent } from './account/account.component';
import { PaymentComponent } from './payment/payment.component';
import { NotificationComponent } from './notification/notification.component';
import { AuditComponent } from './audit/audit.component';

export const routes: Routes = [
  // Auth routes
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },

  // Main routes (no children)
  { path: 'dashboard', component: DashboardComponent },
  { path: 'customer', component: CustomerComponent },
  { path: 'account', component: AccountComponent },
  { path: 'payment', component: PaymentComponent },
  { path: 'notification', component: NotificationComponent },
  { path: 'audit', component: AuditComponent },


  // Default route
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  // Wildcard route
  { path: '**', redirectTo: 'login' }
];


