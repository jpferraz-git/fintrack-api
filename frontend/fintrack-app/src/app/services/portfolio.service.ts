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

export interface PortfolioAssetRequest {
	fkUser?: string | null
	symbol: string
	type: string
	quantity: number
	avgPrice: number
}

export interface PortfolioAssetResponse {
	assetId: string
	fkUser: string
	symbol: string
	type: string
	quantity: number | string
	avgPrice: number | string
	createdAt: string
	updatedAt: string
}

@Injectable({
	providedIn: 'root'
})
export class PortfolioService {
	constructor(private http: HttpClient) {}

	getAssets(): Observable<PortfolioAssetResponse[]> {
		return this.http.get<PortfolioAssetResponse[]>(`${environment.apiUrl}/asset`)
	}

	createAsset(payload: PortfolioAssetRequest): Observable<PortfolioAssetResponse> {
		return this.http.post<PortfolioAssetResponse>(`${environment.apiUrl}/asset`, payload)
	}

	calculateTotalProfitValue(): Observable<PortfolioCalculationResponse> {
		return this.http.get<PortfolioCalculationResponse>(
			`${environment.apiUrl}/asset/calculate-total-profit-value`
		)
	}

	calculateTotalProfitPercentage(): Observable<PortfolioCalculationResponse> {
		return this.http.get<PortfolioCalculationResponse>(
			`${environment.apiUrl}/asset/calculate-total-profit-percentage`
		)
	}

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
