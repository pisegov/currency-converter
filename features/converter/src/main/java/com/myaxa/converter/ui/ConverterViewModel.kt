package com.myaxa.converter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.converter.data.CurrencyRepository
import com.myaxa.converter.data.model.Rates
import com.myaxa.converter.domain.CurrencyConverter
import com.myaxa.converter.domain.DecimalFormatter
import com.myaxa.converter.ui.model.Command
import com.myaxa.converter.ui.model.ConversionInfoUi
import com.myaxa.converter.ui.model.ConversionOperationStatus
import com.myaxa.converter.ui.model.Effect
import com.myaxa.converter.ui.model.Event
import com.myaxa.converter.ui.model.State
import com.myaxa.converter.ui.model.amountIsValid
import com.myaxa.converter.ui.model.toDomainModel
import com.myaxa.ui.onFailure
import com.myaxa.ui.takeSuccess
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ConverterViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val currencyConverter: CurrencyConverter,
    private val decimalFormatter: DecimalFormatter,
    private val reducer: Reducer,
) : ViewModel() {

    private val _state =
        MutableStateFlow(State(ConversionInfoUi.empty(), ConversionOperationStatus.Idle))
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effects = Channel<Effect>()
    val effects: Flow<Effect> = _effects.receiveAsFlow().distinctUntilChanged()

    fun obtainUserEvent(userEvent: Event.User) = reduce(userEvent)

    private fun reduce(event: Event) = viewModelScope.launch {
        _state.update { reducer.reduce(event, it, actor) }
    }

    private fun sendEffect(effect: Effect) = _effects.trySend(effect)

    @OptIn(ObsoleteCoroutinesApi::class)
    private val actor: SendChannel<Command> = viewModelScope.actor {
        for (command in channel) {

            when (command) {

                is Command.FormatDecimalString -> reduce(
                    Event.System.DecimalStringFormatResult(decimalFormatter.format(command.string))
                )

                is Command.PerformConversion -> performConversion(command.conversionInfo)
            }
        }
    }

    private fun performConversion(conversionInfo: ConversionInfoUi) {

        if (!conversionInfo.amountIsValid()) {
            reduce(Event.System.AmountValidationError)
            return
        }

        reduce(Event.System.StartLoading)
        repository.getLatestCurrency(conversionInfo.fromCurrency)
            .onFailure { reduce(Event.System.LoadingError(it)) }
            .takeSuccess()
            .map { rates: Rates ->
                currencyConverter.convert(
                    rates,
                    conversionInfo = conversionInfo.toDomainModel()
                )
            }
            .onEach {
                reduce(Event.System.StopLoading)
                sendEffect(Effect.NavigateToResult(it))
            }
            .launchIn(viewModelScope)
    }
}
