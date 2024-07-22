package com.myaxa.converter.domain.usecase

import com.myaxa.converter.data.CurrencyRepository
import com.myaxa.converter.data.model.Rates
import com.myaxa.converter.domain.CurrencyConverter
import com.myaxa.domain.ConversionInfo
import com.myaxa.domain.ConversionResult
import com.myaxa.util.mapResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class PerformConversionUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val currencyConverter: CurrencyConverter,
) {
    operator fun invoke(conversionInfo: ConversionInfo): Flow<Result<ConversionResult>> {

        return repository.getLatestCurrency(conversionInfo.fromCurrency)
            .mapResult { rates: Rates ->
                currencyConverter.convert(
                    rates = rates,
                    conversionInfo = conversionInfo,
                )
            }
            .flowOn(Dispatchers.IO)
    }
}
