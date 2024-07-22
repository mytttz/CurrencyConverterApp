package com.example.currencyconverterapp.dataclasses

data class CurrencyConvert(
    val base_code: String,
    val target_code: String,
    val conversion_rate: Double,
)