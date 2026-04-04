import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { environment } from '../../environments/environment'

export interface MarketIndividualPrice {
    symbol: string
    price: number | string
}

export interface MarketIndividualKlineResponse {
    openTime: string,
    open: number | string,
    high: number | string,
    low: number | string,
    close: number | string,
    volume: number | string,
    closeTime: string,
    quoteAssetVolume: number | string,
    numberOfTrades: number,
    takerBuyBaseAssetVolume: number | string,
    takerBuyQuoteAssetVolume: number | string
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

    getKlines(symbol: string, interval: string): Observable<MarketIndividualKlineResponse> {
        return this.http.get<MarketIndividualKlineResponse>(`${environment.apiUrl}/binance/klines`, {
            params: {
                symbol,
                interval
            }
        })
    }


}
