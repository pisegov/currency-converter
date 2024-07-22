package com.myaxa.converter.ui.mvi

import com.myaxa.converter.ui.model.ConversionInfoUi

internal sealed interface Command {

    data class PerformConversion(val conversionInfo: ConversionInfoUi) : Command

    data class FormatDecimalString(val string: String) : Command
}
