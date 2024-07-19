package com.myaxa.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(

    @SerialName("data")
    val rates: Rates
)
