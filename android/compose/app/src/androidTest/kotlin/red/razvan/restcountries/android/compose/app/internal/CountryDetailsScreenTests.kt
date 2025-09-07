// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.mock.declare
import red.razvan.restcountries.android.compose.app.AppModule
import red.razvan.restcountries.android.compose.app.R
import red.razvan.restcountries.android.compose.app.internal.screens.details.CountryDetailsScreen
import red.razvan.restcountries.android.compose.design.RestCountriesTheme
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.domain.ObserveDetailedCountryByIdOrNull
import red.razvan.restcountries.domain.RefreshDetailedCountryById
import red.razvan.restcountries.testresources.android.koin.KoinTestRule
import red.razvan.restcountries.testresources.domain.FailingRefreshDetainedCountryById
import red.razvan.restcountries.testresources.domain.SampleData
import red.razvan.restcountries.testresources.domain.SampleDataObserveDetailedCountryByIdOrNull
import red.razvan.restcountries.testresources.domain.SuccessfulRefreshDetailedCountryById
import red.razvan.restcountries.android.compose.design.R as DesignR

@RunWith(AndroidJUnit4::class)
class CountryDetailsScreenTests : KoinTest {

  @get:Rule
  val koinTestRule = KoinTestRule(modules = listOf(AppModule))

  @get:Rule
  val composeTestRule = createComposeRule()

  private lateinit var context: Context
  private lateinit var commonFinders: CommonFinders

  private val expectedCountry = SampleData.DetailedCountries.Italy

  @Before
  fun before() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
    commonFinders = CommonFinders(
      composeTestRule = composeTestRule,
      context = context,
    )
  }

  @Test
  fun withoutNetworkFailure_assertDetailsAreDisplayed() {
    val expectedCountry = SampleData.DetailedCountries.Italy

    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull()
    }
    declare<RefreshDetailedCountryById> {
      SuccessfulRefreshDetailedCountryById(emitDelayInMillis = 100L)
    }

    composeTestRule.setContent {
      TestContent(countryId = expectedCountry.id)
    }

    Thread.sleep(200L)

    commonFinders
      .findSnackbar()
      .assertIsNotDisplayed()

    composeTestRule
      .onNodeWithText(text = expectedCountry.officialName)
      .assertIsDisplayed()
  }

  @Test
  fun requestRefreshFromDropdownMenuAction() {
    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull()
    }
    declare<RefreshDetailedCountryById> {
      SuccessfulRefreshDetailedCountryById(emitDelayInMillis = 100L)
    }

    composeTestRule.setContent {
      TestContent(countryId = expectedCountry.id)
    }

    Thread.sleep(300L)

    commonFinders
      .findMoreOptionsButton()
      .performClick()

    composeTestRule
      .onNodeWithText(context.getString(DesignR.string.refresh_item_label))
      .performClick()

    commonFinders
      .findPullToRefreshIndicator()
      .assertIsDisplayed()
  }

  @Test
  fun requestRefreshFromSwipeDown() {
    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull()
    }
    declare<RefreshDetailedCountryById> {
      SuccessfulRefreshDetailedCountryById(emitDelayInMillis = 100L)
    }

    composeTestRule.setContent {
      TestContent(countryId = expectedCountry.id)
    }

    Thread.sleep(300L)

    swipeToRefresh()

    commonFinders
      .findPullToRefreshIndicator()
      .assertIsDisplayed()
  }

  @Test
  fun withNetworkFailure_assertSnackbarIsDisplayed() {
    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull()
    }
    declare<RefreshDetailedCountryById> {
      FailingRefreshDetainedCountryById()
    }

    composeTestRule.setContent {
      TestContent(countryId = expectedCountry.id)
    }

    commonFinders
      .findSnackbar()
      .assertIsDisplayed()
  }

  @Test
  fun requestRefreshFromNetworkErrorSnackbarAction() {
    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull()
    }
    declare<RefreshDetailedCountryById> {
      FailingRefreshDetainedCountryById()
    }

    composeTestRule.setContent {
      TestContent(countryId = expectedCountry.id)
    }

    composeTestRule
      .onNodeWithText(
        text = context.getString(R.string.retry_button_text),
      )
      .performClick()

    commonFinders
      .findSnackbar()
      .assertIsNotDisplayed()

    commonFinders
      .findPullToRefreshIndicator()
      .assertIsDisplayed()
  }

  private fun swipeToRefresh() {
    composeTestRule
      .onNodeWithTag("content")
      .performTouchInput { swipeDown() }
  }

  @Composable
  private fun TestContent(
    countryId: CountryId,
    modifier: Modifier = Modifier,
  ) {
    RestCountriesTheme {
      CountryDetailsScreen(
        modifier = modifier,
        onNavigateUp = {},
        countryId = countryId,
      )
    }
  }
}
