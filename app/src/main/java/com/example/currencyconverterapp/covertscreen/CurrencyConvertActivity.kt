package com.example.currencyconverterapp.covertscreen

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.network.ApiService
import com.example.currencyconverterapp.network.CurrencyRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class CurrencyConvertActivity : AppCompatActivity() {
    private lateinit var quantityFromText: TextInputEditText
    private lateinit var currencyFromText: AutoCompleteTextView
    private lateinit var currencyToText: AutoCompleteTextView
    private lateinit var covertButton: MaterialButton
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_convert_activity)
        quantityFromText = findViewById(R.id.quantity_from_text)
        currencyFromText = findViewById(R.id.currency_from_text)
        currencyToText = findViewById(R.id.currency_to_text)
        covertButton = findViewById(R.id.covert_button)
        result = findViewById(R.id.result)
        val viewModel = CurrencyConvertViewModel(CurrencyRepository(ApiService.create()))
        viewModel.currencyConvert.observe(this) {
            val quantity = quantityFromText.text.toString().toInt()
            val conversionRate = it.conversion_rate
            val convertedQuantity = quantity * conversionRate
            val symbols = DecimalFormatSymbols().apply {
                decimalSeparator = '.'
            }
            val decimalFormat = DecimalFormat("#.##", symbols)
            val formattedQuantity = decimalFormat.format(convertedQuantity)

            val resultText = StringBuilder()
                .append(quantity)
                .append(" ")
                .append(it.base_code)
                .append(" ≈ ")
                .append(formattedQuantity)
                .append(" ")
                .append(it.target_code)
                .toString()

            result.text = resultText
        }
        covertButton.setOnClickListener {
            val currencyFrom = currencyFromText.text.toString()
            val currencyTo = currencyToText.text.toString()
            if (currencyFrom.isNotEmpty() && currencyTo.isNotEmpty() && quantityFromText.text.toString()
                    .toInt() >= 0 && quantityFromText.text?.isNotEmpty() == true
            ) {
                viewModel.convert(currencyFrom, currencyTo)
            } else {
                Toast.makeText(this, "Entered data is incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}