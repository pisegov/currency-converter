package com.myaxa.converter.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.myaxa.converter.ui.ConverterScreen
import com.myaxa.converter.ui.mvi.ConverterScreenEffect
import kotlinx.serialization.Serializable

@Serializable
data object ConverterScreenRoute

fun NavGraphBuilder.converterScreenDestination(
    effectsHandler: (ConverterScreenEffect) -> Unit,
    modifier: Modifier = Modifier,
) {

    composable<ConverterScreenRoute> {
        ConverterScreen(handleEffect = effectsHandler, modifier = modifier)
    }
}
