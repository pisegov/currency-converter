package com.myaxa.currency_converter

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.myaxa.currency_converter.di.DaggerApplicationComponent
import com.myaxa.ui.viewModel.ViewModelFactoryProvider

class CurrencyConverterApplication : Application(), ViewModelFactoryProvider {
    private val component by lazy { DaggerApplicationComponent.factory().create() }

    override fun provideViewModelFactory(): ViewModelProvider.Factory =
        component.viewModelFactory
}
