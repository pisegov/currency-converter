package com.myaxa.domain

/**
 * Information required for currency conversion
 *
 * @param amount Amount to be converted
 * @param fromCurrency Base currency
 * @param toCurrency Target currency
 */
data class ConversionInfo(
    val amount: Double,
    val fromCurrency: Currency,
    val toCurrency: Currency,
)
