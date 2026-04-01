import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-portfolio-sidebar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './portfolio-sidebar.html',
  styleUrl: './portfolio-sidebar.css'
})
export class PortfolioSidebar {}
