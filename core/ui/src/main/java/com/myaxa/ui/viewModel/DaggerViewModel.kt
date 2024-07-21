package com.myaxa.ui.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified T : ViewModel> daggerViewModel(
    viewModelFactory: ViewModelProvider.Factory =
        (LocalContext.current.applicationContext as ViewModelFactoryProvider)
            .provideViewModelFactory(),
): T = viewModel<T>(factory = viewModelFactory)
