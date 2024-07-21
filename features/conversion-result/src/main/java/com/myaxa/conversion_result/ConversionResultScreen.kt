package com.myaxa.conversion_result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myaxa.conversion_result.navigation.ConversionResultRoute
import com.myaxa.domain.Currency
import com.myaxa.ui.R as CoreUiR

@Composable
internal fun ConversionResultScreen(
    arguments: ConversionResultRoute,
    modifier: Modifier = Modifier,
    handleEffect: (ConversionResultScreenEffect) -> Unit = {},
) {

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = CoreUiR.dimen.content_vertical_padding),
                horizontal = dimensionResource(id = CoreUiR.dimen.content_horizontal_padding),
            )
    ) {
        Text(
            text = "Conversion Result",
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            lineHeight = 32.sp,
        )
        Text(
            text = "${arguments.amount} ${arguments.fromCurrency} ≈ ${arguments.result} ${arguments.toCurrency}",
            fontSize = 20.sp,
            lineHeight = 20.sp,
        )
        Button(onClick = { handleEffect(ConversionResultScreenEffect.Back) }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back",
                )
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(text = "Go back")
            }
        }
    }
}

@Preview
@Composable
private fun ConversionResultScreenPreview() {
    Surface {
        ConversionResultScreen(
            ConversionResultRoute(
                amount = "1.0", fromCurrency = Currency.USD.name, toCurrency = Currency.RUB.name, result = "87.0"
            )
        )
    }
}
