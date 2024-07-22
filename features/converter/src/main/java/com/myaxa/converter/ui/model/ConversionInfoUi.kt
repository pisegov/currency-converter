package com.myaxa.converter.ui.model

import androidx.compose.runtime.Immutable
import com.myaxa.domain.ConversionInfo
import com.myaxa.domain.Currency

/**
 * A ui model of information required for currency conversion
 *
 * @param amountString Amount to be converted presented as a string
 * @param fromCurrency Base currency
 * @param toCurrency Target currency
 */
@Immutable
internal data class ConversionInfoUi(
    val amountString: String,
    val fromCurrency: Currency,
    val toCurrency: Currency,
) {
    companion object {
        private const val INITIAL_AMOUNT = "1"
        fun empty() = ConversionInfoUi(INITIAL_AMOUNT, Currency.USD, Currency.RUB)
    }
}

internal fun ConversionInfoUi.amountIsValid(): Boolean =
    amountString.toDoubleOrNull() != null

internal fun ConversionInfoUi.toDomainModel(): ConversionInfo {
    assert(amountIsValid())

    return ConversionInfo(
        amount = amountString.toDouble(),
        fromCurrency = fromCurrency,
        toCurrency = toCurrency,
    )
}
