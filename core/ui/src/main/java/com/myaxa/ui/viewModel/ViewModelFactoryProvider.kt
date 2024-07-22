package com.myaxa.ui.viewModel

import androidx.lifecycle.ViewModelProvider

/**
 * Provides a [UniversalViewModelFactory] instance
 * that creates every viewmodel in the app
 */
interface ViewModelFactoryProvider {
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
