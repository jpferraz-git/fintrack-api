import { Routes } from '@angular/router';
import { AlertsPage } from '../pages/alerts/alerts.component';
import { DashboardPage } from '../pages/dashboard/dashboard.component';
import { ForgotPasswordPage } from '../pages/forgot-password/forgot-password.component';
import { LoginPage } from '../pages/login/login.component';
import { PortfolioPage } from '../pages/portfolio/portfolio.component';
import { SettingsPage } from '../pages/settings/settings.component';
import { SignupPage } from '../pages/signup/signup.component';

export const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: 'dashboard' },
	{ path: 'dashboard', component: DashboardPage },
	{ path: 'portfolio', component: PortfolioPage },
	{ path: 'alerts', component: AlertsPage },
	{ path: 'settings', component: SettingsPage },
	{ path: 'forgot-password', component: ForgotPasswordPage },
	{ path: 'login', component: LoginPage },
	{ path: 'signup', component: SignupPage },
	{ path: '**', redirectTo: 'dashboard' }
];
