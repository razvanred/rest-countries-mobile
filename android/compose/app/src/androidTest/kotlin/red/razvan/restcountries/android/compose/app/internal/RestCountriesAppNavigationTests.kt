// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import red.razvan.restcountries.android.compose.app.AppModule
import red.razvan.restcountries.android.compose.app.R
import red.razvan.restcountries.android.compose.app.RestCountriesApp
import red.razvan.restcountries.domain.ObserveCountryListItems
import red.razvan.restcountries.domain.ObserveDetailedCountryByIdOrNull
import red.razvan.restcountries.domain.RefreshCountryListItems
import red.razvan.restcountries.domain.RefreshDetailedCountryById
import red.razvan.restcountries.testresources.android.koin.KoinTestRule
import red.razvan.restcountries.testresources.domain.SampleData
import red.razvan.restcountries.testresources.domain.SampleDataObserveCountryListItems
import red.razvan.restcountries.testresources.domain.SampleDataObserveDetailedCountryByIdOrNull
import red.razvan.restcountries.testresources.domain.SuccessfulRefreshCountryListItems
import red.razvan.restcountries.testresources.domain.SuccessfulRefreshDetailedCountryById

class RestCountriesAppNavigationTests : KoinTest {
  private companion object {
    val InstrumentedTestModule = module {
      single<ObserveCountryListItems> {
        SampleDataObserveCountryListItems()
      }

      single<RefreshCountryListItems> {
        SuccessfulRefreshCountryListItems()
      }

      single<ObserveDetailedCountryByIdOrNull> {
        SampleDataObserveDetailedCountryByIdOrNull()
      }

      single<RefreshDetailedCountryById> {
        SuccessfulRefreshDetailedCountryById()
      }
    }
  }

  @get:Rule
  val koinTestRule = KoinTestRule(modules = listOf(AppModule, InstrumentedTestModule))

  @get:Rule
  val composeTestRule = createComposeRule()

  private lateinit var context: Context

  @Before
  fun before() {
    context = InstrumentationRegistry.getInstrumentation().context

    composeTestRule.setContent {
      RestCountriesApp()
    }
  }

  @Test(expected = NoActivityResumedException::class)
  fun countriesScreen_back_quitsApp() {
    Espresso.pressBack()
  }

  @Test
  fun countriesScreen_itemClick_showsCountryDetailsScreen() {
    // Navigate to Romania details screen
    composeTestRule
      .onNodeWithText(text = SampleData.CountryListItems.Romania.officialName)
      .performClick()

    composeTestRule
      .onNodeWithText(text = context.getString(R.string.country_details_screen_title))
      .assertExists()
  }

  @Test
  fun countryDetailsScreen_back_showsCountriesScreen() {
    // Navigate to Romania details screen
    composeTestRule
      .onNodeWithText(text = SampleData.CountryListItems.Romania.officialName)
      .performClick()

    Espresso.pressBack()

    composeTestRule
      .onNodeWithText(text = context.getString(R.string.countries_list_screen_title))
      .assertExists()
  }
}
