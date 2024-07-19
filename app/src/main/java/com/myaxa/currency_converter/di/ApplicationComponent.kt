package com.myaxa.currency_converter.di

import com.myaxa.network.NetworkDataSource
import dagger.Component
import dagger.Module
import javax.inject.Scope

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(

        ): ApplicationComponent
    }

    val networkDataSource: NetworkDataSource
}

@Module(includes = [NetworkModule::class])
interface ApplicationModule

@Scope
annotation class ApplicationScope
