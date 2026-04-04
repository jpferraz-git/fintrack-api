import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { environment } from '../../environments/environment'

export interface MarketIndividualPrice {
    symbol: string
    price: number | string
}

export interface MarketIndividualLineChart {
    symbol: string,
    opentime: Date | number,
    open: number,
    high: number,
    low: number,
    close: number,
    volume: number,
    closetime: Date | number,
    quoteAssetVolume: number,
    numberOfTrades: number,
    takerBuyBaseAssetVolume: number,
    takerBuyQuoteAssetVolume: number
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

    getKlines(symbol: string, interval: string): Observable<MarketIndividualLineChart[]> {
        return this.http.get<MarketIndividualLineChart[]>(`${environment.apiUrl}/binance/klines`, {
            params: {
                symbol,
                interval
            }
        })
    }
}
