package com.myaxa.domain

data class ConversionInfo(
    val amount: Double,
    val fromCurrency: Currency,
    val toCurrency: Currency,
)
