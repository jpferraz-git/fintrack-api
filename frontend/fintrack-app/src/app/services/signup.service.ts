import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { environment } from '../../environments/environment'

interface SignupRequest {
    name: string
    email: string
    password: string
}

@Injectable({
    providedIn: 'root'
})

export class SignupService {

    constructor(private http: HttpClient) {}

    singUp(body: SignupRequest): Observable<void> {
        return this.http.post<void>(`${environment.apiUrl}/auth/register`, body)
    }
}