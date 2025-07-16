// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import red.razvan.restcountries.compose.app.AppModule
import red.razvan.restcountries.compose.app.internal.screens.countries.CountriesScreen
import red.razvan.restcountries.compose.app.internal.screens.countries.CountriesScreenUiState
import red.razvan.restcountries.compose.design.RestCountriesTheme
import red.razvan.restcountries.testresources.android.koin.KoinTestRule
import red.razvan.restcountries.testresources.domain.SampleData

@RunWith(AndroidJUnit4::class)
class CountriesScreenTests : KoinTest {

  @get:Rule(order = 0)
  val koinTestRule = KoinTestRule(modules = listOf(AppModule))

  @get:Rule(order = 1)
  val composeTestRule = createComposeRule()

  @Test
  fun displayCountryListItems() {
    val items = listOf(
      SampleData.CountryListItems.Italy,
      SampleData.CountryListItems.Romania,
    )

    composeTestRule.setContent {
      RestCountriesTheme {
        CountriesScreen(
          state = CountriesScreenUiState(
            items = items,
            isRefreshing = false,
            networkFailure = null,
            isDropdownMenuExpanded = false,
          ),
          onRefresh = {},
          onNetworkFailureMessageDismissal = {},
          onNavigateToCountryDetails = {},
          onDropdownMenuExpandedChange = {},
        )
      }
    }

    items.forEach { item ->
      composeTestRule
        .onNodeWithText(text = item.officialName)
        .assertIsDisplayed()
    }
  }
}
