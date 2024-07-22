package com.myaxa.converter.ui.mvi

import com.myaxa.converter.ui.model.ConversionInfoUi

/**
 * A command to the [actor][com.myaxa.converter.ui.ConverterViewModel.actor] to perform an operation
 */
internal sealed interface Command {

    data class PerformConversion(val conversionInfo: ConversionInfoUi) : Command

    data class FormatDecimalString(val string: String) : Command
}
