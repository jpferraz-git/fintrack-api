import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { environment } from '../../environments/environment'

export interface PortfolioCalculationRequest {
	symbol: string
}

export interface PortfolioCalculationResponse {
	value: number | string
}

@Injectable({
	providedIn: 'root'
})
export class PortfolioService {
	constructor(private http: HttpClient) {}

	calculateProfitValue(symbol: string): Observable<PortfolioCalculationResponse> {
		return this.http.post<PortfolioCalculationResponse>(
			`${environment.apiUrl}/asset/calculate-profit-value`,
			{ symbol }
		)
	}

	calculateProfitPercentage(symbol: string): Observable<PortfolioCalculationResponse> {
		return this.http.post<PortfolioCalculationResponse>(
			`${environment.apiUrl}/asset/calculate-profit-percentage`,
			{ symbol }
		)
	}
}
