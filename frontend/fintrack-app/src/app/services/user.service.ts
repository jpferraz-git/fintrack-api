import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { environment } from '../../environments/environment'

interface UserUpdateRequest {
    name: string
    email: string
}

interface UserUpdateByEmailRequest {
    name?: string
    email?: string
    password?: string
}

@Injectable({
    providedIn: 'root'
})

export class UserService {

    constructor(private http: HttpClient) {}

    updateUser(body: UserUpdateRequest): Observable<void> {
        return this.http.put<void>(`${environment.apiUrl}/users/update`, body)
    }

    updateUserByEmail(email: string, body: UserUpdateByEmailRequest): Observable<void> {
        return this.http.put<void>(`${environment.apiUrl}/users/${encodeURIComponent(email)}`, body)
    }

    updatePasswordByEmail(email: string, newPassword: string): Observable<void> {
        return this.updateUserByEmail(email, { password: newPassword })
    }

    deleteUserByEmail(email: string): Observable<void> {
        return this.http.delete<void>(`${environment.apiUrl}/users?email=${encodeURIComponent(email)}`)
    }

}