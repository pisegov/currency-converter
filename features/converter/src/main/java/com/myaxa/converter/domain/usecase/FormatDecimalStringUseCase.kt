package com.myaxa.converter.domain.usecase

import com.myaxa.converter.domain.DecimalFormatter
import javax.inject.Inject

internal class FormatDecimalStringUseCase @Inject constructor(
    private val decimalFormatter: DecimalFormatter,
) {
    operator fun invoke(string: String): String {
        return decimalFormatter.format(string)
    }
}
