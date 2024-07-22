package com.myaxa.converter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response to [com.myaxa.converter.data.CurrencyApi.getLatest] call
 * @param rates latest rates for each supported currency
 */
@Serializable
data class CurrencyResponse(

    @SerialName("data")
    val rates: Rates
)
