import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { environment } from '../../environments/environment'

interface UserUpdateRequest {
    name: string
    email: string
    password: string
}

@Injectable({
    providedIn: 'root'
})

export class UpdateUserService {

    constructor(private http: HttpClient) {}

    updateUser(body: UserUpdateRequest): Observable<void> {
        return this.http.put<void>(`${environment.apiUrl}/users/update`, body)
    }
}