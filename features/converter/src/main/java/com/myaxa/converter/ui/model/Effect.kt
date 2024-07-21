package com.myaxa.converter.ui.model

import com.myaxa.domain.ConversionResult

sealed interface Effect {

    data class NavigateToResult(val result: ConversionResult): Effect
}
