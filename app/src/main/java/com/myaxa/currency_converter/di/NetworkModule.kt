package com.myaxa.currency_converter.di

import com.myaxa.currency_converter.BuildConfig
import com.myaxa.network.ApiKeyInterceptor
import com.myaxa.network.NetworkDataSource
import com.myaxa.network.RetrofitHolder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor


@Module(includes = [NetworkBindInterceptorsModule::class])
class NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor =
        ApiKeyInterceptor(apiKey = BuildConfig.API_KEY)

    @Provides
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    fun provideNetworkDataSource(retrofitHolder: RetrofitHolder): NetworkDataSource =
        NetworkDataSource(retrofitHolder = retrofitHolder)
}

@Module
interface NetworkBindInterceptorsModule {
    @Binds
    @IntoSet
    fun bindHttpLoggingInterceptor(impl: HttpLoggingInterceptor): Interceptor

    @Binds
    @IntoSet
    fun bindApiKeyInterceptor(impl: ApiKeyInterceptor): Interceptor
}
