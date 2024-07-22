package com.myaxa.converter.ui.mvi

import com.myaxa.domain.Currency

internal sealed interface Event {

    sealed interface User : Event {

        data class UpdateAmount(val value: String) : User

        data class UpdateFromCurrency(val value: Currency) : User

        data class UpdateToCurrency(val value: Currency) : User

        data object SetNetworkErrorShown : User

        data object Convert : User
    }

    sealed interface System : Event {

        data object StartLoading : System

        data object StopLoading : System

        data class LoadingError(val throwable: Throwable) : System

        data class DecimalStringFormatResult(val formattedString: String) : System

        data class AmountValidationError(val messageStringResourceId: Int) : System
    }
}
