package com.myaxa.converter.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myaxa.domain.Currency

@Composable
internal fun CurrencyDropdown(
    currency: Currency,
    modifier: Modifier = Modifier,
    topText: String = "",
    onOptionSelected: (Currency) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        if (topText.isNotEmpty()) Text(text = topText)
        Box(contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = currency.name)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Currency dropdown menu",
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    Currency.entries.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(text = it.name)
                            },
                            onClick = {
                                expanded = false

                                onOptionSelected(it)
                            },
                        )
                    }
                }
            }
        }
    }
}
