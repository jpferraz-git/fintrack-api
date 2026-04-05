import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable, tap } from 'rxjs'
import { environment } from '../../environments/environment'

interface LoginRequest {
    email: string
    password: string
}

interface LoginResponse {
    token: string
}

@Injectable({
    providedIn: 'root'
})

export class AuthService {

    private readonly tokenKey = 'token'

    constructor(private http: HttpClient) {}

    login(body: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(`${environment.apiUrl}/auth/login`, body)
            .pipe(
                tap(response => {
                    this.saveToken(response.token)
                })
            )
    }

    saveToken(token: string): void {
        if (!this.isBrowserStorageAvailable()) {
            return
        }

        localStorage.setItem(this.tokenKey, token)
    }

    getToken(): string | null {
        if (!this.isBrowserStorageAvailable()) {
            return null
        }

        return localStorage.getItem(this.tokenKey)
    }

    logout(): void {
        if (!this.isBrowserStorageAvailable()) {
            return
        }

        localStorage.removeItem(this.tokenKey)
    }

    private isBrowserStorageAvailable(): boolean {
        return typeof window !== 'undefined' && typeof localStorage !== 'undefined'
    }

}