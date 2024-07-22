package com.myaxa.converter.domain.model

internal sealed interface DecimalStringValidationResult {

    data object Valid : DecimalStringValidationResult

    sealed interface Error : DecimalStringValidationResult {
        data object EmptyString : Error
        data object NoNumbers : Error
        data object WrongNumber : Error
    }
}
