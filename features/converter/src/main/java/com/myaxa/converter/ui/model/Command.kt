package com.myaxa.converter.ui.model

internal sealed interface Command {

    data class PerformConversion(val conversionInfo: ConversionInfoUi) : Command

    data class FormatDecimalString(val string: String) : Command
}
