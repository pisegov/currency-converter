package com.myaxa.currency_converter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.myaxa.conversion_result.ConversionResultScreenEffect
import com.myaxa.conversion_result.navigation.conversionResultDestination
import com.myaxa.conversion_result.navigation.navigateToConversionResult
import com.myaxa.converter.navigation.ConverterScreenRoute
import com.myaxa.converter.navigation.converterScreenDestination
import com.myaxa.converter.ui.model.ConverterScreenEffect

@Composable
fun CurrencyConverterNavigation(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = ConverterScreenRoute) {
        converterScreenDestination(
            effectsHandler = { effect ->
                when (effect) {
                    is ConverterScreenEffect.NavigateToResult -> {
                        with(effect.result) {
                            navController.navigateToConversionResult(
                                amount = info.amount.toString(),
                                fromCurrency = info.fromCurrency.name,
                                toCurrency = info.toCurrency.name,
                                result = result.toString(),
                            )
                        }
                    }
                }
            },
            modifier = modifier,
        )

        conversionResultDestination(
            effectsHandler = { effect ->
                when (effect) {
                    ConversionResultScreenEffect.Back -> {
                        navController.navigateUp()
                    }
                }
            },
            modifier = modifier,
        )

    }
}
