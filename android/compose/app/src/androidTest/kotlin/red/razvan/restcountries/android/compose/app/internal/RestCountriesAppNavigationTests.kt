// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.core.net.toUri
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.rule.IntentsRule
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import red.razvan.restcountries.android.compose.app.AppModule
import red.razvan.restcountries.android.compose.app.R
import red.razvan.restcountries.android.compose.app.RestCountriesApp
import red.razvan.restcountries.android.domain.ObserveArtifacts
import red.razvan.restcountries.domain.ObserveCountryListItems
import red.razvan.restcountries.domain.ObserveDetailedCountryByIdOrNull
import red.razvan.restcountries.domain.RefreshCountryListItems
import red.razvan.restcountries.domain.RefreshDetailedCountryById
import red.razvan.restcountries.testresources.android.domain.SampleDataObserveArtifacts
import red.razvan.restcountries.testresources.android.koin.KoinTestRule
import red.razvan.restcountries.testresources.domain.SampleData
import red.razvan.restcountries.testresources.domain.SampleDataObserveCountryListItems
import red.razvan.restcountries.testresources.domain.SampleDataObserveDetailedCountryByIdOrNull
import red.razvan.restcountries.testresources.domain.SuccessfulRefreshCountryListItems
import red.razvan.restcountries.testresources.domain.SuccessfulRefreshDetailedCountryById
import red.razvan.restcountries.android.compose.design.R as DesignR
import red.razvan.restcountries.testresources.android.domain.SampleData as AndroidSampleData

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

      single<ObserveArtifacts> {
        SampleDataObserveArtifacts()
      }
    }
  }

  @get:Rule
  val koinTestRule = KoinTestRule(modules = listOf(AppModule, InstrumentedTestModule))

  @get:Rule
  val composeTestRule = createComposeRule()

  @get:Rule
  val intentsRule = IntentsRule()

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
  fun countriesScreen_licensesMenuItemClick_showsLicensesScreen() {
    composeTestRule
      .onNodeWithContentDescription(context.getString(DesignR.string.more_options_button_cd))
      .performClick()

    composeTestRule
      .onNodeWithText(text = context.getString(R.string.licenses_item_label))
      .performClick()

    composeTestRule
      .onNodeWithText(text = context.getString(R.string.licenses_screen_title))
      .assertExists()
  }

  @Test
  fun licensesScreen_back_showsCountriesScreen() {
    composeTestRule
      .onNodeWithContentDescription(context.getString(DesignR.string.more_options_button_cd))
      .performClick()

    composeTestRule
      .onNodeWithText(text = context.getString(R.string.licenses_item_label))
      .performClick()

    Espresso.pressBack()

    composeTestRule
      .onNodeWithText(text = context.getString(R.string.countries_list_screen_title))
      .assertExists()
  }

  @Test
  fun licensesScreen_itemClick_opensBrowser() {
    val license = AndroidSampleData.Artifacts.AndroidxCompose.artifacts.first()

    val browserIntentMatcher = allOf(
      hasAction(Intent.ACTION_VIEW),
      hasData(license.scm!!.url.toUri()),
    )

    // Mocks the web browser due to non explicable infinite wait time
    intending(browserIntentMatcher)
      .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

    composeTestRule
      .onNodeWithContentDescription(context.getString(DesignR.string.more_options_button_cd))
      .performClick()

    composeTestRule
      .onNodeWithText(text = context.getString(R.string.licenses_item_label))
      .performClick()

    composeTestRule
      .onNodeWithText(text = license.name!!)
      .performClick()

    // Checks if the browser is actually launched from the app
    intended(browserIntentMatcher)
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
