package com.myaxa.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * Creates every view model in the application
 * @param viewModelProviders map of viewmodel providers for each viewmodel class
 */
@Suppress("UNCHECKED_CAST")
class UniversalViewModelFactory @Inject constructor(
    private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return viewModelProviders.getValue(modelClass).get() as T
    }
}

/**
 * Exports [UniversalViewModelFactory] as [ViewModelProvider.Factory]
 * so that every feature can use it without depending on this module
 */
@Module
interface UniversalViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(impl: UniversalViewModelFactory): ViewModelProvider.Factory
}

/**
 * Designed to annotate [Binds] annotated methods that bind viewmodel implementations
 * into map of class-provider pairs for [UniversalViewModelFactory]
 *
 * Could be moved to other module to separate it from [UniversalViewModelFactory]
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
