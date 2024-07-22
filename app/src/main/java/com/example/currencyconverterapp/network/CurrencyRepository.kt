package com.example.currencyconverterapp.network

import com.example.currencyconverterapp.dataclasses.CurrencyConvert
import retrofit2.Response

class CurrencyRepository(private val apiService: ApiService) {

    suspend fun getCurrencyConvert(
        fromCurrency: String,
        toCurrency: String
    ): Response<CurrencyConvert> {
        return apiService.getCurrencyConvert(fromCurrency = fromCurrency, toCurrency = toCurrency)
    }
}