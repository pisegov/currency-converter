package com.myaxa.converter.ui.mvi

import androidx.compose.runtime.Immutable
import com.myaxa.converter.ui.model.ConversionInfoUi
import com.myaxa.converter.ui.model.ConversionOperationStatus

/**
 * A model for screen state
 */
@Immutable
internal data class State(

    val conversionInfo: ConversionInfoUi,

    val conversionOperationStatus: ConversionOperationStatus,
)
