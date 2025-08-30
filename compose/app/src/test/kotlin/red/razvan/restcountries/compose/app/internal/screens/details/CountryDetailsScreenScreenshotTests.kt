// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import coil3.ColorImage
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.annotation.DelicateCoilApi
import coil3.test.FakeImageLoaderEngine
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import red.razvan.restcountries.compose.design.RestCountriesTheme
import red.razvan.restcountries.data.models.DetailedCountry
import red.razvan.restcountries.data.models.NetworkFailure
import red.razvan.restcountries.testresources.domain.SampleData

class CountryDetailsScreenScreenshotTests {

  @get:Rule
  val paparazzi = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5,
  )

  private val country: DetailedCountry
    get() = SampleData.DetailedCountries.Italy

  private val networkFailure: NetworkFailure
    get() = NetworkFailure.WithHttpStatusCode(code = 500, exception = null)

  @OptIn(DelicateCoilApi::class)
  @Before
  fun before() {
    val engine = FakeImageLoaderEngine.Builder()
      .intercept(country.flag.svg, ColorImage(Color.Red.toArgb()))
      .build()
    val imageLoader = ImageLoader.Builder(paparazzi.context)
      .components { add(engine) }
      .build()
    SingletonImageLoader.setUnsafe(imageLoader)
  }

  @Test
  fun `Data loaded`() {
    paparazzi.snapshot {
      TestContent(
        state = CountryDetailsUiState(
          country = country,
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
            state = CountryDetailsUiState(
              country = country,
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
            state = CountryDetailsUiState(
              country = country,
              isRefreshing = false,
              isDropdownMenuExpanded = false,
              networkFailure = networkFailure,
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
        state = CountryDetailsUiState(
          country = country,
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
        state = CountryDetailsUiState(
          country = null,
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
            state = CountryDetailsUiState(
              country = null,
              isRefreshing = false,
              isDropdownMenuExpanded = false,
              networkFailure = networkFailure,
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
        state = CountryDetailsUiState(
          country = null,
          isRefreshing = true,
          isDropdownMenuExpanded = false,
          networkFailure = null,
        ),
      )
    }
  }

  @Composable
  private fun TestContent(
    state: CountryDetailsUiState,
    modifier: Modifier = Modifier,
  ) {
    RestCountriesTheme {
      CountryDetailsScreen(
        state = state,
        modifier = modifier,
        onRefresh = {},
        onDropdownMenuExpandedChange = {},
        onNetworkFailureMessageDismissal = {},
        onNavigateUp = {},
      )
    }
  }
}
