package com.myaxa.converter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.myaxa.converter.ui.model.ConversionInfoUi
import com.myaxa.converter.ui.model.Event
import com.myaxa.ui.R as CoreUiR

@Composable
internal fun DataInputFields(
    conversionInfo: ConversionInfoUi,
    sendUserEvent: (Event.User) -> Unit,
    modifier: Modifier = Modifier,
    clearFocus: () -> Unit = {},
    isError: Boolean = false,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        AmountTextField(
            text = conversionInfo.amountString,
            onValueChange = { sendUserEvent(Event.User.UpdateAmount(it)) },
            onDone = {
                sendUserEvent(Event.User.Convert)
                clearFocus()
            },
            isError = isError,
        )
        CurrencyDropdown(
            currency = conversionInfo.fromCurrency,
            topText = stringResource(id = CoreUiR.string.from),
            onOptionSelected = { sendUserEvent(Event.User.UpdateFromCurrency(it)) },
        )
        CurrencyDropdown(
            currency = conversionInfo.toCurrency,
            topText = stringResource(id = CoreUiR.string.to),
            onOptionSelected = { sendUserEvent(Event.User.UpdateToCurrency(it)) },
        )
    }
}
