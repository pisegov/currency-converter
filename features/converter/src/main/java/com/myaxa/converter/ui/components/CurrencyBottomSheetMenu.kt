package com.myaxa.converter.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myaxa.domain.Currency
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyBottomSheetMenu(
    currentCurrency: Currency,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    entries: List<Currency> = Currency.entries,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onOptionSelected: (Currency) -> Unit = {},
) {

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
    ) {
        LazyColumn {
            entries.forEach {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                val currency = it
                                scope
                                    .launch { sheetState.hide() }
                                    .invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            onDismissRequest()
                                        }
                                        onOptionSelected(currency)
                                    }
                            }
                            .padding(horizontal = 32.dp, vertical = 8.dp)
                            .fillMaxWidth()

                    ) {
                        Text(text = it.name)
                        Spacer(modifier = Modifier.padding(4.dp))
                        if (it == currentCurrency) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Current currency",
                            )
                        }
                    }
                }
            }
        }
    }
}
