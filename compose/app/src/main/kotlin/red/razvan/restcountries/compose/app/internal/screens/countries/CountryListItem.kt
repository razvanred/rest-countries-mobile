// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal.screens.countries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import red.razvan.restcountries.compose.design.RestCountriesTheme

@Composable
internal fun CountryListItem(
  officialName: String,
  emojiFlag: String,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .padding(
        horizontal = 20.dp,
        vertical = 12.dp,
      )
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = emojiFlag,
      fontSize = 24.sp,
    )
    Text(
      text = officialName,
      style = MaterialTheme.typography.titleSmall,
    )
  }
}

@Preview(
  showBackground = true,
)
@Composable
private fun CountryListItemPreview() {
  RestCountriesTheme {
    CountryListItem(
      officialName = "Italy",
      emojiFlag = "🇮🇹",
    )
  }
}
