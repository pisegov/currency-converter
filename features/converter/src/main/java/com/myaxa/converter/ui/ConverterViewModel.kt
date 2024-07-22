package com.myaxa.converter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.converter.domain.model.DecimalStringValidationResult
import com.myaxa.converter.domain.usecase.DecimalStringValidateUseCase
import com.myaxa.converter.domain.usecase.FormatDecimalStringUseCase
import com.myaxa.converter.domain.usecase.PerformConversionUseCase
import com.myaxa.converter.ui.model.ConversionInfoUi
import com.myaxa.converter.ui.model.ConversionOperationStatus
import com.myaxa.converter.ui.model.toDomainModel
import com.myaxa.converter.ui.mvi.Command
import com.myaxa.converter.ui.mvi.ConverterScreenEffect
import com.myaxa.converter.ui.mvi.Event
import com.myaxa.converter.ui.mvi.Reducer
import com.myaxa.converter.ui.mvi.State
import com.myaxa.converter.ui.util.DecimalStringValidationErrorMapper
import com.myaxa.util.onFailure
import com.myaxa.util.onSuccess
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ConverterViewModel @Inject constructor(
    private val performConversionUseCase: PerformConversionUseCase,
    private val decimalStringFormatUseCase: FormatDecimalStringUseCase,
    private val decimalStringValidateUseCase: DecimalStringValidateUseCase,
    private val validationResultMapper: DecimalStringValidationErrorMapper,
    private val reducer: Reducer,
) : ViewModel() {

    private val _state =
        MutableStateFlow(State(ConversionInfoUi.empty(), ConversionOperationStatus.Idle))
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effects = Channel<ConverterScreenEffect>()
    val effects: Flow<ConverterScreenEffect> = _effects.receiveAsFlow().distinctUntilChanged()

    fun obtainUserEvent(userEvent: Event.User) = reduce(userEvent)

    private fun reduce(event: Event) = viewModelScope.launch {
        _state.update { reducer.reduce(event, it, actor) }
    }

    private fun sendEffect(effect: ConverterScreenEffect) = _effects.trySend(effect)

    @OptIn(ObsoleteCoroutinesApi::class)
    private val actor: SendChannel<Command> = viewModelScope.actor {
        for (command in channel) {

            when (command) {

                is Command.FormatDecimalString -> reduce(
                    Event.System.DecimalStringFormatResult(
                        formattedString = decimalStringFormatUseCase(
                            string = command.string,
                        )
                    )
                )

                is Command.PerformConversion -> performConversion(
                    conversionInfo = command.conversionInfo,
                )
            }
        }
    }

    private fun performConversion(conversionInfo: ConversionInfoUi) {

        val validationResult = decimalStringValidateUseCase(conversionInfo.amountString)
        if (validationResult is DecimalStringValidationResult.Error) {
            reduce(
                Event.System.AmountValidationError(
                    messageStringResourceId = validationResultMapper
                        .mapErrorToMessage(validationResult)
                )
            )
            return
        }

        reduce(Event.System.StartLoading)
        performConversionUseCase(conversionInfo = conversionInfo.toDomainModel())
            .onSuccess {
                reduce(Event.System.StopLoading)
                sendEffect(ConverterScreenEffect.NavigateToResult(it))
            }
            .onFailure { reduce(Event.System.LoadingError(it)) }
            .launchIn(viewModelScope)
    }
}
