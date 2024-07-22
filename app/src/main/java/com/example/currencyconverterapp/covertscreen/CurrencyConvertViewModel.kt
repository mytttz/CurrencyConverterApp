package com.example.currencyconverterapp.covertscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.dataclasses.CurrencyConvert
import com.example.currencyconverterapp.network.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CurrencyConvertViewModel(
    private val repository: CurrencyRepository,
) : ViewModel() {

    private val _currencyConvert = MutableLiveData<CurrencyConvert>()
    val currencyConvert: LiveData<CurrencyConvert> get() = _currencyConvert

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun convert(fromCurrency: String, toCurrency: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response = repository.getCurrencyConvert(fromCurrency, toCurrency)
                    if (response.isSuccessful) {
                        _currencyConvert.postValue(response.body())
                    } else {
                        _error.postValue("Failed to convert: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                _error.postValue("Error occurred: ${e.message}")
            }
        }
    }
}