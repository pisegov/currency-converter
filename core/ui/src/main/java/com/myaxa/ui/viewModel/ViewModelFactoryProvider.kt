package com.myaxa.ui.viewModel

import androidx.lifecycle.ViewModelProvider


interface ViewModelFactoryProvider {
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
