// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.design

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun ConnectedCardGroup(
  cards: List<ConnectableCardProperties>,
  modifier: Modifier = Modifier,
  title: (@Composable () -> Unit)? = null,
) {
  val itemCornerShapeSize = 16.dp

  Column(modifier = modifier) {
    if (title != null) {
      CompositionLocalProvider(
        LocalTextStyle provides MaterialTheme.typography.labelLarge,
        LocalContentColor provides MaterialTheme.colorScheme.primary,
      ) {
        Box(
          modifier = Modifier
            .padding(
              vertical = 12.dp,
            ),
        ) {
          title()
        }
      }
    }

    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
      cards.forEachIndexed { i, properties ->
        ConnectableCard(
          properties = properties,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(
            topStart = if (i == 0) itemCornerShapeSize else 0.dp,
            topEnd = if (i == 0) itemCornerShapeSize else 0.dp,
            bottomStart = if (i == cards.lastIndex) itemCornerShapeSize else 0.dp,
            bottomEnd = if (i == cards.lastIndex) itemCornerShapeSize else 0.dp,
          ),
        )
      }
    }
  }
}

data class ConnectableCardProperties(
  val content: @Composable ColumnScope.() -> Unit,
  val innerPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
)

@Composable
private fun ConnectableCard(
  properties: ConnectableCardProperties,
  modifier: Modifier = Modifier,
  shape: Shape = CardDefaults.shape,
  colors: CardColors = CardDefaults.cardColors(),
) {
  Card(
    modifier = modifier,
    shape = shape,
    colors = colors,
  ) {
    Column(
      modifier = Modifier
        .padding(properties.innerPadding),
      content = properties.content,
    )
  }
}
