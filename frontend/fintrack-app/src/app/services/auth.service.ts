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
    user: UserProfileResponse
}


interface UserProfileResponse {
    name: string
    email: string
    createdAt: string | Date
    updatedAt: string | Date
}

@Injectable({
    providedIn: 'root'
})

export class AuthService {

    private readonly tokenKey = 'token'
    user: UserProfileResponse | null = null

    constructor(private http: HttpClient) {}

    login(body: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(`${environment.apiUrl}/auth/login`, body)
            .pipe(
                tap(response => {
                    this.saveToken(response.token)
                    this.setUser(response.user)
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

    getUser() {
        if (!this.isBrowserStorageAvailable()) {
            return null
        }
        const user = localStorage.getItem('user')
        console.log('Getting user from AuthService:', user)
        return user ? JSON.parse(user) : null
    }

    setUser(user: UserProfileResponse): void {
        if (!this.isBrowserStorageAvailable()) {
            return
        }
        console.log('Setting user in AuthService:', user)
        localStorage.setItem('user', JSON.stringify(user))
    }

    logout(): void {
        if (!this.isBrowserStorageAvailable()) {
            return
        }

        localStorage.removeItem(this.tokenKey)
        localStorage.removeItem('user')
    }

    private isBrowserStorageAvailable(): boolean {
        return typeof window !== 'undefined' && typeof localStorage !== 'undefined'
    }

}