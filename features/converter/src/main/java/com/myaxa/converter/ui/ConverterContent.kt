package com.myaxa.converter.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myaxa.converter.R
import com.myaxa.converter.ui.components.DataInputFields
import com.myaxa.converter.ui.model.ConversionInfoUi
import com.myaxa.converter.ui.model.ConversionOperationStatus
import com.myaxa.converter.ui.mvi.Event
import com.myaxa.converter.ui.mvi.State
import com.myaxa.ui.R as CoreUiR

@Composable
internal fun ConverterContent(
    state: State,
    modifier: Modifier = Modifier,
    sendUserEvent: (Event.User) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                focusManager.clearFocus()
            }
    ) {

        if (state.conversionOperationStatus is ConversionOperationStatus.Loading) {
            CircularProgressIndicator(
                Modifier.padding(
                    top = dimensionResource(id = CoreUiR.dimen.progress_indicator_top_padding),
                )
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = dimensionResource(id = CoreUiR.dimen.content_vertical_padding),
                    horizontal = dimensionResource(id = CoreUiR.dimen.content_horizontal_padding),
                )
        ) {

            Text(
                text = stringResource(id = R.string.converter_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )

            val amountValidationErrorText = (state.conversionOperationStatus
                    as? ConversionOperationStatus.AmountValidationError)
                ?.messageStringResourceId?.let {
                    stringResource(id = it)
                }
            DataInputFields(
                conversionInfo = state.conversionInfo,
                sendUserEvent = sendUserEvent,
                amountValidationErrorText = amountValidationErrorText,
                clearFocus = { focusManager.clearFocus() },
            )

            Button(
                onClick = { sendUserEvent(Event.User.Convert) },
            ) {
                Text(
                    text = stringResource(id = R.string.convert),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ConverterContentPreview() {
    Surface {
        ConverterContent(state = State(ConversionInfoUi.empty(), ConversionOperationStatus.Idle))
    }
}
