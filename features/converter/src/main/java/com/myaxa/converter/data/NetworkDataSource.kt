package com.myaxa.converter.data

import com.myaxa.converter.data.model.CurrencyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class NetworkDataSource(private val api: CurrencyApi) {

    suspend fun getLatestCurrencyRates(base: String? = null): Result<CurrencyResponse> =
        withContext(Dispatchers.IO) {
            api.getLatest(base)
        }
}
