package com.myaxa.converter.domain

import javax.inject.Inject

internal class DecimalFormatter @Inject constructor() {

    fun format(s: String): String {
        val string = s
            .trim()
            .replace(',', '.')
            .filterMultiplePoints()

        val pointIndex = string.indexOf('.')
        if (pointIndex < 0) return string

        return if (pointIndex + 3 < string.length) {
            string.substring(0, pointIndex + 3)
        } else {
            string
        }
    }

    private fun String.filterMultiplePoints(): String {
        var hasPoint = false
        return filter {
            val itIsPoint = it == '.'
            if (hasPoint && itIsPoint) return@filter false

            if (itIsPoint) hasPoint = true

            true
        }
    }
}
