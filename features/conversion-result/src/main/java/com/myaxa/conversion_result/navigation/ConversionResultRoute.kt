package com.myaxa.conversion_result.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.myaxa.conversion_result.ConversionResultScreen
import com.myaxa.conversion_result.ConversionResultScreenEffect
import kotlinx.serialization.Serializable

@Serializable
data class ConversionResultRoute(
    val amount: String,
    val fromCurrency: String,
    val toCurrency: String,
    val result: String,
)

fun NavGraphBuilder.conversionResultDestination(
    effectsHandler: (ConversionResultScreenEffect) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<ConversionResultRoute> { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<ConversionResultRoute>()

        ConversionResultScreen(arguments = args, modifier = modifier, handleEffect = effectsHandler)
    }
}

fun NavController.navigateToConversionResult(
    amount: String,
    fromCurrency: String,
    toCurrency: String,
    result: String,
) {
    navigate(
        route = ConversionResultRoute(
            amount = amount,
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            result = result,
        )
    )
}
