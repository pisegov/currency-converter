package com.myaxa.converter.ui.util

import com.myaxa.converter.R
import com.myaxa.converter.domain.model.DecimalStringValidationResult
import javax.inject.Inject

/**
 * Maps error objects to resource ids for error message
 */
internal class DecimalStringValidationErrorMapper @Inject constructor() {
    fun mapErrorToMessage(validationResult: DecimalStringValidationResult.Error): Int {
        return when (validationResult) {
            DecimalStringValidationResult.Error.EmptyString -> R.string.amount_is_empty

            DecimalStringValidationResult.Error.WrongNumber ->
                R.string.amount_is_incorrect

            DecimalStringValidationResult.Error.NoNumbers -> R.string.amount_contains_no_numbers
        }
    }
}
