import { Routes } from '@angular/router';
import { AlertsPage } from '../pages/alerts/alerts';
import { DashboardPage } from '../pages/dashboard/dashboard';
import { LoginPage } from '../pages/login/login';
import { PortfolioPage } from '../pages/portfolio/portfolio';
import { SignupPage } from '../pages/signup/signup';

export const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: 'dashboard' },
	{ path: 'dashboard', component: DashboardPage },
	{ path: 'portfolio', component: PortfolioPage },
	{ path: 'alerts', component: AlertsPage },
	{ path: 'login', component: LoginPage },
	{ path: 'signup', component: SignupPage },
	{ path: '**', redirectTo: 'dashboard' }
];
