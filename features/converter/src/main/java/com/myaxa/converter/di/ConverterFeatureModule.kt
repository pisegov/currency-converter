package com.myaxa.converter.di

import androidx.lifecycle.ViewModel
import com.myaxa.converter.data.CurrencyApi
import com.myaxa.converter.data.NetworkDataSource
import com.myaxa.converter.ui.ConverterViewModel
import com.myaxa.network.RetrofitHolder
import com.myaxa.ui.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.create

/**
 * Module for exporting feature components
 * so that [Application module][com.myaxa.currency_converter.di.ApplicationModule] can resolve
 * dependencies for [ConverterViewModel]
 */
@Module
abstract class ConverterFeatureModule {

    /**
     * Binds [ConverterViewModel] to common [ViewModel]
     * for [UniversalViewModelFactory][com.myaxa.ui.viewModel.UniversalViewModelFactory]
     *
     * @see ViewModelKey
     */
    @Binds
    @IntoMap
    @ViewModelKey(ConverterViewModel::class)
    internal abstract fun bindConverterViewModel(impl: ConverterViewModel): ViewModel

    companion object {

        @Provides
        internal fun provideNetworkDataSource(retrofitHolder: RetrofitHolder): NetworkDataSource {

            val api: CurrencyApi = retrofitHolder.retrofit.create()

            return NetworkDataSource(api = api)
        }
    }
}
