package com.myaxa.converter.domain

import com.myaxa.converter.data.model.Rates
import com.myaxa.domain.ConversionInfo
import com.myaxa.domain.ConversionResult
import com.myaxa.domain.Currency
import com.myaxa.domain.Currency.*
import javax.inject.Inject
import kotlin.math.round

internal class CurrencyConverter @Inject constructor() {

    fun convert(
        rates: Rates,
        conversionInfo: ConversionInfo,
    ): ConversionResult {

        val rate = getRate(currency = conversionInfo.toCurrency, rates = rates)
        val result = round(conversionInfo.amount * rate * 100) / 100

        return ConversionResult(conversionInfo, result)
    }

    private fun getRate(currency: Currency, rates: Rates): Double = when (currency) {
        AUD -> rates.AUD
        BGN -> rates.BGN
        BRL -> rates.BRL
        CAD -> rates.CAD
        CHF -> rates.CHF
        CNY -> rates.CNY
        CZK -> rates.CZK
        DKK -> rates.DKK
        EUR -> rates.EUR
        GBP -> rates.GBP
        HKD -> rates.HKD
        HRK -> rates.HRK
        HUF -> rates.HUF
        IDR -> rates.IDR
        ILS -> rates.ILS
        INR -> rates.INR
        ISK -> rates.ISK
        JPY -> rates.JPY
        KRW -> rates.KRW
        MXN -> rates.MXN
        MYR -> rates.MYR
        NOK -> rates.NOK
        NZD -> rates.NZD
        PHP -> rates.PHP
        PLN -> rates.PLN
        RON -> rates.RON
        RUB -> rates.RUB
        SEK -> rates.SEK
        SGD -> rates.SGD
        THB -> rates.THB
        TRY -> rates.TRY
        USD -> rates.USD
        ZAR -> rates.ZAR
    }
}
