package com.myaxa.converter.ui.model

import com.myaxa.domain.ConversionResult

sealed interface ConverterScreenEffect {

    data class NavigateToResult(val result: ConversionResult): ConverterScreenEffect
}
