package com.myaxa.converter.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.myaxa.converter.ui.model.ConversionOperationStatus.*
import com.myaxa.converter.ui.model.ConverterScreenEffect
import com.myaxa.converter.ui.model.Event
import com.myaxa.ui.viewModel.daggerViewModel

@Composable
internal fun ConverterScreen(
    modifier: Modifier = Modifier,
    viewModel: ConverterViewModel = daggerViewModel(),
    handleEffect: (ConverterScreenEffect) -> Unit,
) {

    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.effects.collect { effect ->
            handleEffect(effect)
        }
    }

    when (val status = uiState.conversionOperationStatus) {
        is NetworkError -> {
            Toast.makeText(LocalContext.current, status.message, Toast.LENGTH_SHORT).show()
            viewModel.obtainUserEvent(Event.User.SetNetworkErrorShown)
        }

        Idle, Loading, AmountValidationError -> Unit
    }

    ConverterContent(
        state = uiState,
        modifier = modifier,
        sendUserEvent = { viewModel.obtainUserEvent(it) }
    )
}
