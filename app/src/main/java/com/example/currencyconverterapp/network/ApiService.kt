package com.example.currencyconverterapp.network

import com.example.currencyconverterapp.dataclasses.CurrencyConvert
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{token}/pair/{from}/{to}")
    suspend fun getCurrencyConvert(
        @Path("token") apiKey: String = TOKEN,
        @Path("from") fromCurrency: String,
        @Path("to") toCurrency: String
    ): Response<CurrencyConvert>

    companion object {
        private const val TOKEN = "1384ea31834c32e84868e0f7"
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/v6/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}