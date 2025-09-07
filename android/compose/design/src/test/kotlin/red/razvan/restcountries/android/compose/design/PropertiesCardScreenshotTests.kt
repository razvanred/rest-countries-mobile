// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.design

import androidx.compose.foundation.layout.ColumnScope
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
import red.razvan.restcountries.android.compose.design.PropertiesCard
import red.razvan.restcountries.android.compose.design.Property
import red.razvan.restcountries.android.compose.design.RestCountriesTheme

class PropertiesCardScreenshotTests {

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
  fun `With title and properties`() {
    paparazzi.snapshot {
      TestContent(
        title = {
          TestTitle()
        },
        properties = {
          TestProperties()
        },
      )
    }
  }

  @Test
  fun `With properties`() {
    paparazzi.snapshot {
      TestContent(
        properties = {
          TestProperties()
        },
      )
    }
  }

  @Composable
  private fun TestContent(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    properties: @Composable ColumnScope.() -> Unit = {},
  ) {
    RestCountriesTheme {
      PropertiesCard(
        modifier = modifier
          .padding(all = 8.dp)
          .fillMaxWidth(),
        title = title,
        properties = properties,
      )
    }
  }

  @Composable
  private fun TestTitle() {
    Text(text = "Contacts")
  }

  @Composable
  private fun TestProperties() {
    Property(
      title = {
        Text("Phone number")
      },
      value = {
        Text(
          text = "555-0100",
        )
      },
    )
    Property(
      title = {
        Text("E-mail address")
      },
      value = {
        Text(
          text = "mail@test.com",
        )
      },
    )
  }
}
