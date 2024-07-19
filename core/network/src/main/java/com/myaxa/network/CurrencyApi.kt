package com.myaxa.network

import com.myaxa.network.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest")
    suspend fun getLatest(@Query("base_currency") base: String?): Result<CurrencyResponse>
}
