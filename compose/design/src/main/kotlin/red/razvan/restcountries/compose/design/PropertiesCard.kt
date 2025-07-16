// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.design

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PropertiesCard(
  properties: @Composable ColumnScope.() -> Unit,
  modifier: Modifier = Modifier,
  title: (@Composable () -> Unit)? = null,
) {
  Card(
    modifier = modifier.fillMaxWidth(),
  ) {
    Column(
      modifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth(),
    ) {
      if (title != null) {
        Box(
          modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp,
          ),
        ) {
          ProvideTextStyle(MaterialTheme.typography.titleMedium) {
            title()
          }
        }
      }
      properties()
    }
  }
}

@Composable
fun Property(
  title: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  value: (@Composable () -> Unit)? = null,
) {
  Column(
    modifier = modifier
      .padding(all = 16.dp)
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(4.dp),
  ) {
    ProvideTextStyle(MaterialTheme.typography.labelLarge) {
      title()
    }
    ProvideTextStyle(MaterialTheme.typography.bodySmall) {
      value?.invoke()
    }
  }
}
