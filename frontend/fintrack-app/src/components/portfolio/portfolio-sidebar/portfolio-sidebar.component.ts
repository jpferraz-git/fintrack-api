import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-portfolio-sidebar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './portfolio-sidebar.component.html',
  styleUrl: './portfolio-sidebar.component.css'
})
export class PortfolioSidebar {}
