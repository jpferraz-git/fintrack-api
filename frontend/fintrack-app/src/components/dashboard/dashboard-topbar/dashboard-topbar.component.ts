import { Component } from '@angular/core';
import { ClockComponent } from './dashboard-clock/dashboard-clock.component';

@Component({
  selector: 'app-dashboard-topbar',
  imports: [ClockComponent],
  templateUrl: './dashboard-topbar.component.html',
  styleUrl: './dashboard-topbar.component.css'
})
export class DashboardTopbar {}