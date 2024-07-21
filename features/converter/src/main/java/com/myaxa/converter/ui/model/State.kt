package com.myaxa.converter.ui.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class State(

    val conversionInfo: ConversionInfoUi,

    val conversionOperationStatus: ConversionOperationStatus,
)
