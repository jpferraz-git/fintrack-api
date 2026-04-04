import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { environment } from '../../environments/environment'

export interface MarketIndividualPrice {
    symbol: string
    price: number | string
}

@Injectable({
    providedIn: 'root'
})
export class MarketDataService {

    constructor(private http: HttpClient) {}

    getPrice(symbol: string): Observable<MarketIndividualPrice> {
        return this.http.get<MarketIndividualPrice>(`${environment.apiUrl}/binance/price`, {
            params: {
                symbol
            }
        })
    }
}
