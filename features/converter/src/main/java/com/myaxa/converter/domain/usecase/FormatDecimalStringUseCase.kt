package com.myaxa.converter.domain.usecase

import javax.inject.Inject

internal class FormatDecimalStringUseCase @Inject constructor() {

    companion object {
        private const val POINT_CHARACTER = '.'
        private const val COMMA_CHARACTER = ','
        private const val DECIMAL_PLACES_OFFSET = 3
        private const val STRING_START = 0
    }

    operator fun invoke(string: String): String {
        return format(string)
    }

    private fun format(s: String): String {
        val string = s
            .trim()
            .replace(COMMA_CHARACTER, POINT_CHARACTER)
            .filterMultiplePoints()

        val pointIndex = string.indexOf(POINT_CHARACTER)
        if (pointIndex < 0) return string

        return if (pointIndex + DECIMAL_PLACES_OFFSET < string.length) {
            string.substring(STRING_START, pointIndex + DECIMAL_PLACES_OFFSET)
        } else {
            string
        }
    }

    private fun String.filterMultiplePoints(): String {
        var hasPoint = false
        return filter {
            val itIsPoint = it == POINT_CHARACTER
            if (hasPoint && itIsPoint) return@filter false

            if (itIsPoint) hasPoint = true

            true
        }
    }
}
