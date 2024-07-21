package com.myaxa.converter.data

import com.myaxa.converter.data.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest")
    suspend fun getLatest(@Query("base_currency") base: String?): Result<CurrencyResponse>
}
