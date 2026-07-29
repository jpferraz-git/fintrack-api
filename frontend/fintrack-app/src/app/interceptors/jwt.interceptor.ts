import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

constructor(private authService: AuthService) {}

intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const isAuthRequest = req.url.includes('/login') || req.url.includes('/signup');
    
    if (isAuthRequest) {
        return next.handle(req);
    }

    const authReq = req.clone({
        withCredentials: true
    });

    return next.handle(authReq);
    }
}