import { Routes } from '@angular/router';
import { LoginPage } from '../pages/login/login';
import { SignupPage } from '../pages/signup/signup';

export const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: 'login' },
	{ path: 'login', component: LoginPage },
	{ path: 'signup', component: SignupPage },
	{ path: '**', redirectTo: 'login' }
];
