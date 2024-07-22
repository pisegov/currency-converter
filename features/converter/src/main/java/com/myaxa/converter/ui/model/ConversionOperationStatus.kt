package com.myaxa.converter.ui.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface ConversionOperationStatus {

    data object Idle : ConversionOperationStatus

    data object Loading : ConversionOperationStatus

    data object NetworkError : ConversionOperationStatus

    data class AmountValidationError(val messageStringResourceId: Int) : ConversionOperationStatus
}
