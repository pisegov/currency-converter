package com.myaxa.currency_converter.di

import com.myaxa.currency_converter.BuildConfig
import com.myaxa.network.ApiKeyInterceptor
import com.myaxa.network.RetrofitHolder
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
class NetworkModule {

    @Provides
    @IntoSet
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    @Provides
    @IntoSet
    fun provideApiKeyInterceptor(): Interceptor =
        ApiKeyInterceptor(apiKey = BuildConfig.API_KEY)

    @Provides
    fun provideRetrofitHolder(
        okHttpInterceptors: Set<@JvmSuppressWildcards Interceptor>,
    ): RetrofitHolder = RetrofitHolder(BuildConfig.BASE_URL, okHttpInterceptors)
}
