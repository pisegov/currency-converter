package com.myaxa.currency_converter.di

import androidx.lifecycle.ViewModelProvider
import com.myaxa.converter.di.ConverterFeatureModule
import com.myaxa.ui.viewModel.UniversalViewModelFactoryModule
import dagger.Component
import dagger.Module
import javax.inject.Scope

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(): ApplicationComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}

@Module(
    includes = [
        NetworkModule::class,
        UniversalViewModelFactoryModule::class,
        ConverterFeatureModule::class,
    ]
)
interface ApplicationModule

@Scope
annotation class ApplicationScope
