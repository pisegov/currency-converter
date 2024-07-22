package com.myaxa.converter.ui.mvi

import com.myaxa.domain.ConversionResult

/**
 * A side effects that needs to be processed once
 */
sealed interface ConverterScreenEffect {

    data class NavigateToResult(val result: ConversionResult): ConverterScreenEffect
}
