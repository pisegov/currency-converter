package com.myaxa.converter.ui.mvi

import com.myaxa.converter.ui.model.ConversionOperationStatus
import kotlinx.coroutines.channels.SendChannel
import javax.inject.Inject

/**
 * Responsible for every state update
 */
internal class Reducer @Inject constructor() {

    /**
     * A method to update [screen state][com.myaxa.converter.ui.ConverterViewModel.state]
     * based on the given event
     * @param event state update event
     * @param state current state value
     * @param actor entity for complicated operations processing
     */
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

        is Event.System.AmountValidationError -> {
            state.copy(
                conversionOperationStatus = ConversionOperationStatus.AmountValidationError(
                    messageStringResourceId = event.messageStringResourceId
                )
            )
        }
    }
}
