package com.myaxa.converter.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.myaxa.ui.R as CoreUiR

@Composable
internal fun AmountTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDone: KeyboardActionScope.() -> Unit = {},
    isError: Boolean = false,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier.widthIn(100.dp, 150.dp),
        label = { Text(text = stringResource(id = CoreUiR.string.amount)) },
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = CoreUiR.string.invalid_amount),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (isError) {
                Icon(
                    Icons.Filled.Info,
                    stringResource(id = CoreUiR.string.error),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        keyboardActions = KeyboardActions(onDone = onDone),
        isError = isError,
    )
}
