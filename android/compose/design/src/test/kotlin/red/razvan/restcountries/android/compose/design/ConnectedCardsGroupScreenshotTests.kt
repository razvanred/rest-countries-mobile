// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.design

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule
import org.junit.Test

class ConnectedCardsGroupScreenshotTests {

  @get:Rule
  val paparazzi = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5,
    renderingMode = SessionParams.RenderingMode.SHRINK,
  )

  @Test
  fun `Without title and without properties`() {
    paparazzi.snapshot {
      TestContent()
    }
  }

  @Test
  fun `With title`() {
    paparazzi.snapshot {
      TestContent(
        title = {
          TestTitle()
        },
      )
    }
  }

  @Test
  fun `With title and cards`() {
    paparazzi.snapshot {
      TestContent(
        title = {
          TestTitle()
        },
        cards = testCards,
      )
    }
  }

  @Test
  fun `With cards`() {
    paparazzi.snapshot {
      TestContent(
        cards = testCards,
      )
    }
  }

  @Composable
  private fun TestContent(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    cards: List<ConnectableCardProperties> = emptyList(),
  ) {
    RestCountriesTheme {
      ConnectedCardGroup(
        modifier = modifier
          .padding(all = 8.dp)
          .fillMaxWidth(),
        title = title,
        cards = cards,
      )
    }
  }

  @Composable
  private fun TestTitle() {
    Text(text = "Countries")
  }

  private val testCards = listOf(
    ConnectableCardProperties(
      content = {
        Text(text = "Italy")
      },
    ),
    ConnectableCardProperties(
      content = {
        Text(text = "Spain")
      },
    ),
    ConnectableCardProperties(
      content = {
        Text(text = "France")
      },
    ),
    ConnectableCardProperties(
      content = {
        Text(text = "Germany")
      },
    ),
  )
}
