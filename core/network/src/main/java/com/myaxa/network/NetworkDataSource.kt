package com.myaxa.network

import com.myaxa.network.model.CurrencyResponse
import retrofit2.create

fun NetworkDataSource(retrofitHolder: RetrofitHolder): NetworkDataSource {
    val api: CurrencyApi = retrofitHolder.retrofit.create()

    return NetworkDataSource(api = api)
}

class NetworkDataSource internal constructor(private val api: CurrencyApi) {

    suspend fun getLatest(base: String? = null): Result<CurrencyResponse> {
        return api.getLatest(base)
    }

}
