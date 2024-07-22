package com.myaxa.domain

/**
 * A model for currency conversion operation
 *
 * @param info Information required for conversion
 * @param result Converted amount
 */
data class ConversionResult(
    val info: ConversionInfo,
    val result: Double,
)
