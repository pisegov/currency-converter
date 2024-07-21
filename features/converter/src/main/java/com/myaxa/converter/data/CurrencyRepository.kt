package com.myaxa.converter.data

import com.myaxa.converter.data.model.Rates
import com.myaxa.domain.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class CurrencyRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) {

    fun getLatestCurrency(baseCurrency: Currency = Currency.USD): Flow<Result<Rates>> {
        return flow { emit(networkDataSource.getLatest(baseCurrency.name).map { it.rates }) }
    }
}
