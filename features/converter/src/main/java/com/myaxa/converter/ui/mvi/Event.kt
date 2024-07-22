package com.myaxa.converter.ui.mvi

import com.myaxa.domain.Currency

/**
 * An event for changing the [screen state][com.myaxa.converter.ui.ConverterViewModel.state]
 */
internal sealed interface Event {

    /**
     * A user interaction event
     */
    sealed interface User : Event {

        data class UpdateAmount(val value: String) : User

        data class UpdateFromCurrency(val value: Currency) : User

        data class UpdateToCurrency(val value: Currency) : User

        data object Convert : User

        data object SetNetworkErrorShown : User
    }

    /**
     * Events that comes from [actor][com.myaxa.converter.ui.ConverterViewModel.actor]
     * Designed to return some long operation result to change the screen state
     */
    sealed interface System : Event {

        data object StartLoading : System

        data object StopLoading : System

        data class LoadingError(val throwable: Throwable) : System

        data class DecimalStringFormatResult(val formattedString: String) : System

        data class AmountValidationError(val messageStringResourceId: Int) : System
    }
}
