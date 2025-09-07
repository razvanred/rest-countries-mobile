// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.countries

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test
import red.razvan.restcountries.android.compose.design.RestCountriesTheme
import red.razvan.restcountries.data.models.CountryListItem
import red.razvan.restcountries.data.models.NetworkFailure
import red.razvan.restcountries.testresources.domain.SampleData

class CountriesScreenScreenshotTests {

  @get:Rule
  val paparazzi = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5,
  )

  private val items: List<CountryListItem>
    get() = SampleData.CountryListItems.All

  @Test
  fun `Data loaded`() {
    paparazzi.snapshot {
      TestContent(
        state = CountriesScreenUiState(
          items = items,
          isRefreshing = false,
          isDropdownMenuExpanded = false,
          networkFailure = null,
        ),
      )
    }
  }

  @Test
  fun `Data loaded with dropdown menu expanded`() {
    paparazzi.gif(
      view = ComposeView(paparazzi.context).apply {
        setContent {
          TestContent(
            state = CountriesScreenUiState(
              items = items,
              isRefreshing = false,
              isDropdownMenuExpanded = true,
              networkFailure = null,
            ),
          )
        }
      },
      end = 200L,
    )
  }

  @Test
  fun `Data loaded with network failure`() {
    paparazzi.gif(
      view = ComposeView(paparazzi.context).apply {
        setContent {
          TestContent(
            state = CountriesScreenUiState(
              items = items,
              isRefreshing = false,
              isDropdownMenuExpanded = false,
              networkFailure = NetworkFailure.WithHttpStatusCode(
                code = 500,
                exception = null,
              ),
            ),
          )
        }
      },
      end = 200L,
    )
  }

  @Test
  fun `Refreshing with data loaded`() {
    paparazzi.snapshot {
      TestContent(
        state = CountriesScreenUiState(
          items = items,
          isRefreshing = true,
          isDropdownMenuExpanded = false,
          networkFailure = null,
        ),
      )
    }
  }

  @Test
  fun `No data loaded`() {
    paparazzi.snapshot {
      TestContent(
        state = CountriesScreenUiState(
          items = emptyList(),
          isRefreshing = false,
          isDropdownMenuExpanded = false,
          networkFailure = null,
        ),
      )
    }
  }

  @Test
  fun `No data loaded with network failure`() {
    paparazzi.gif(
      view = ComposeView(paparazzi.context).apply {
        setContent {
          TestContent(
            state = CountriesScreenUiState(
              items = emptyList(),
              isRefreshing = false,
              isDropdownMenuExpanded = false,
              networkFailure = NetworkFailure.WithHttpStatusCode(
                code = 500,
                exception = null,
              ),
            ),
          )
        }
      },
      end = 200L,
    )
  }

  @Test
  fun `Refreshing with no data loaded`() {
    paparazzi.snapshot {
      TestContent(
        state = CountriesScreenUiState(
          items = emptyList(),
          isRefreshing = true,
          isDropdownMenuExpanded = false,
          networkFailure = null,
        ),
      )
    }
  }

  @Composable
  private fun TestContent(
    state: CountriesScreenUiState,
    modifier: Modifier = Modifier,
  ) {
    RestCountriesTheme {
      CountriesScreen(
        state = state,
        modifier = modifier,
        onNetworkFailureMessageDismissal = {},
        onRefresh = {},
        onNavigateToCountryDetails = {},
        onDropdownMenuExpandedChange = {},
      )
    }
  }
}
