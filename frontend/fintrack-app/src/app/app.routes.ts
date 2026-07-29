import { Routes } from '@angular/router';
import { AlertsPage } from '../pages/alerts/alerts.component';
import { DashboardPage } from '../pages/dashboard/dashboard.component';
import { ForgotPasswordPage } from '../pages/forgot-password/forgot-password.component';
import { LoginPage } from '../pages/login/login.component';
import { PortfolioPage } from '../pages/portfolio/portfolio.component';
import { SettingsPage } from '../pages/settings/settings.component';
import { SignupPage } from '../pages/signup/signup.component';

import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: 'dashboard' },
	{ path: 'dashboard', component: DashboardPage, canActivate: [authGuard] },
	{ path: 'portfolio', component: PortfolioPage, canActivate: [authGuard] },
	{ path: 'alerts', component: AlertsPage, canActivate: [authGuard] },
	{ path: 'settings', component: SettingsPage, canActivate: [authGuard] },
	{ path: 'forgot-password', component: ForgotPasswordPage },
	{ path: 'login', component: LoginPage },
	{ path: 'signup', component: SignupPage },
	{ path: '**', redirectTo: 'dashboard' }
];
