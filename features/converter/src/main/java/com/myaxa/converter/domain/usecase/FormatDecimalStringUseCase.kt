package com.myaxa.converter.domain.usecase

import javax.inject.Inject

/**
 * Formats decimal number presented as a string
 * Replaces commas with points, filters multiple points, leaves only 2 decimal places
 *
 * @return an intermediate version of a decimal number that may not be valid for conversion
 * but is valid for further input
 */
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
        // indexOf returns 0, if there is no such character
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
