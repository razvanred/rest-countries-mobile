// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.licenses

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test
import red.razvan.restcountries.android.compose.design.RestCountriesTheme
import red.razvan.restcountries.testresources.android.domain.SampleData

class LicensesScreenScreenshotTests {

  @get:Rule
  val paparazzi = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5,
  )

  @Test
  fun `With licenses`() {
    paparazzi.snapshot {
      TestContent(
        state = LicensesUiState(
          artifacts = SampleData.Artifacts.All,
        ),
      )
    }
  }

  @Test
  fun `Without licenses`() {
    paparazzi.snapshot {
      TestContent(
        state = LicensesUiState(
          artifacts = emptyList(),
        ),
      )
    }
  }

  @Composable
  private fun TestContent(
    state: LicensesUiState,
    modifier: Modifier = Modifier,
  ) {
    RestCountriesTheme {
      LicensesScreen(
        state = state,
        onNavigateUp = {},
        onOpenUrl = {},
        modifier = modifier,
      )
    }
  }
}
