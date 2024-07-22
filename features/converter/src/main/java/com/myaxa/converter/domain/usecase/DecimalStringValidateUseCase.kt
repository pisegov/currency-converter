package com.myaxa.converter.domain.usecase

import com.myaxa.converter.domain.model.DecimalStringValidationResult
import javax.inject.Inject

internal class DecimalStringValidateUseCase @Inject constructor() {
    operator fun invoke(string: String): DecimalStringValidationResult = when {
        string.isBlank() -> {
            DecimalStringValidationResult.Error.EmptyString
        }

        !string.contains(regex = Regex("[0-9]")) -> {
            DecimalStringValidationResult.Error.NoNumbers
        }

        string.toDoubleOrNull() == null -> {
            DecimalStringValidationResult.Error.WrongNumber
        }

        else -> {
            DecimalStringValidationResult.Valid
        }
    }
}
