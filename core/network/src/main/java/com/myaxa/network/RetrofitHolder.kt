package com.myaxa.network

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject

class RetrofitHolder @Inject constructor(
    private val baseUrl: String,
    private val okHttpInterceptors: Set<@JvmSuppressWildcards Interceptor>,
) {
    private val json: Json = Json

    private val okHttpClient = OkHttpClient.Builder().apply {
        okHttpInterceptors.forEach { addInterceptor(it) }
    }.build()


    private val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}
