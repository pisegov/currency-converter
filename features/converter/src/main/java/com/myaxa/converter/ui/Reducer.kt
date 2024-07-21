package com.myaxa.converter.ui

import com.myaxa.converter.ui.model.Command
import com.myaxa.converter.ui.model.ConversionOperationStatus
import com.myaxa.converter.ui.model.Event
import com.myaxa.converter.ui.model.State
import kotlinx.coroutines.channels.SendChannel
import javax.inject.Inject

internal class Reducer @Inject constructor() {

    fun reduce(
        event: Event,
        state: State,
        actor: SendChannel<Command>,
    ): State = when (event) {

        is Event.User.UpdateAmount -> {
            actor.trySend(Command.FormatDecimalString(event.value))
            state
        }

        is Event.User.UpdateFromCurrency -> {
            state.copy(conversionInfo = state.conversionInfo.copy(fromCurrency = event.value))
        }

        is Event.User.UpdateToCurrency -> {
            state.copy(
                conversionInfo = state.conversionInfo.copy(
                    toCurrency = event.value
                )
            )
        }

        Event.System.StartLoading -> {
            state.copy(conversionOperationStatus = ConversionOperationStatus.Loading)
        }

        Event.User.SetNetworkErrorShown, Event.System.StopLoading -> {
            state.copy(conversionOperationStatus = ConversionOperationStatus.Idle)
        }

        Event.User.Convert -> {
            actor.trySend(Command.PerformConversion(state.conversionInfo))
            state
        }

        is Event.System.DecimalStringFormatResult -> {
            state.copy(
                conversionInfo = state.conversionInfo.copy(
                    amountString = event.formattedString
                ),
                conversionOperationStatus = ConversionOperationStatus.Idle,
            )
        }

        is Event.System.LoadingError -> {
            state.copy(
                conversionOperationStatus = ConversionOperationStatus.NetworkError
            )
        }

        Event.System.AmountValidationError -> {
            state.copy(conversionOperationStatus = ConversionOperationStatus.AmountValidationError)
        }
    }
}
