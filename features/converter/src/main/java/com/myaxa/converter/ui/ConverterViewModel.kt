package com.myaxa.converter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.converter.domain.usecase.FormatDecimalStringUseCase
import com.myaxa.converter.domain.usecase.PerformConversionUseCase
import com.myaxa.converter.ui.model.Command
import com.myaxa.converter.ui.model.ConversionInfoUi
import com.myaxa.converter.ui.model.ConversionOperationStatus
import com.myaxa.converter.ui.model.ConverterScreenEffect
import com.myaxa.converter.ui.model.Event
import com.myaxa.converter.ui.model.State
import com.myaxa.converter.ui.model.amountIsValid
import com.myaxa.converter.ui.model.toDomainModel
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

        if (!conversionInfo.amountIsValid()) {
            reduce(Event.System.AmountValidationError)
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
