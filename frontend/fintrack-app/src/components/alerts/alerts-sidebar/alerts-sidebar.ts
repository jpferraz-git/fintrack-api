import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-alerts-sidebar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './alerts-sidebar.html',
  styleUrl: './alerts-sidebar.css'
})
export class AlertsSidebar {}