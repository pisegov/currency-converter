package com.myaxa.converter.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.myaxa.converter.R
import com.myaxa.domain.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CurrencyMenuButton(
    currency: Currency,
    modifier: Modifier = Modifier,
    topText: String = "",
    onOptionSelected: (Currency) -> Unit = {},
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        if (topText.isNotEmpty()) Text(text = topText)
        Box(contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier
                    .clickable { showBottomSheet = true }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = currency.name)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.currency_dropdown_menu),
                )
                if (showBottomSheet) {
                    CurrencyBottomSheetMenu(
                        currentCurrency = currency,
                        onDismissRequest = { showBottomSheet = false },
                        onOptionSelected = onOptionSelected
                    )
                }
            }
        }
    }
}
