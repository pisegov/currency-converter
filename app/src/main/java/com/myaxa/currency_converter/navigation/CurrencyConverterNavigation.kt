package com.myaxa.currency_converter.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.myaxa.converter.navigation.ConverterScreenRoute
import com.myaxa.converter.navigation.converterScreenDestination
import com.myaxa.converter.ui.model.Effect

@Composable
fun CurrencyConverterNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = ConverterScreenRoute) {
        converterScreenDestination(
            effectsHandler = { effect ->
                when (effect) {
                    is Effect.NavigateToResult -> {
                        Toast.makeText(
                            context,
                            "${effect.result.result} ${effect.result.info.toCurrency.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            modifier = modifier,
        )

    }
}
