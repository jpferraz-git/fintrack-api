import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable, tap } from 'rxjs'
import { environment } from '../../environments/environment'
import { UtilsService } from './utils.service'

interface LoginRequest {
    email: string
    password: string
}

interface LoginResponse {
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

    constructor(
        private http: HttpClient,
        private utilsService: UtilsService
    ) {}

    login(body: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(`${environment.apiUrl}/auth/login`, body)
            .pipe(
                tap(response => {
                    this.setUser(response.user)
                })
            )
    }



    getUser() {
        return this.utilsService.getStoredUser<UserProfileResponse>()
    }

    setUser(user: UserProfileResponse): void {
        if (!this.isBrowserStorageAvailable()) {
            return
        }
        console.log('Setting user in AuthService:', user)
        localStorage.setItem('user', JSON.stringify(user))
    }

    logout(): void {
        this.http.post(`${environment.apiUrl}/auth/logout`, {}, { withCredentials: true }).subscribe({
            next: () => console.log('Logged out from server'),
            error: err => console.error('Error logging out from server', err)
        })

        if (!this.isBrowserStorageAvailable()) {
            return
        }

        localStorage.removeItem('user')
    }

    private isBrowserStorageAvailable(): boolean {
        return typeof window !== 'undefined' && typeof localStorage !== 'undefined'
    }

}