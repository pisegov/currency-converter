package com.myaxa.converter.data

import com.myaxa.converter.data.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    /**
     * Get latest currency rates
     *
     * @param base a base currency against which need to get exchange rates
     * @return latest currency rates
     */
    @GET("latest")
    suspend fun getLatest(@Query("base_currency") base: String?): Result<CurrencyResponse>
}
