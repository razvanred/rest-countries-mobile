// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.junit4.createComposeRule
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
import red.razvan.restcountries.android.compose.app.internal.screens.countries.CountriesScreen
import red.razvan.restcountries.android.compose.design.RestCountriesTheme
import red.razvan.restcountries.domain.ObserveCountryListItems
import red.razvan.restcountries.domain.RefreshCountryListItems
import red.razvan.restcountries.testresources.android.koin.KoinTestRule
import red.razvan.restcountries.testresources.domain.FailingRefreshCountryListItems
import red.razvan.restcountries.testresources.domain.SampleData
import red.razvan.restcountries.testresources.domain.SampleDataObserveCountryListItems
import red.razvan.restcountries.testresources.domain.SuccessfulRefreshCountryListItems
import red.razvan.restcountries.android.compose.design.R as DesignR

@RunWith(AndroidJUnit4::class)
class CountriesScreenTests : KoinTest {

  @get:Rule(order = 0)
  val koinTestRule = KoinTestRule(modules = listOf(AppModule))

  @get:Rule(order = 1)
  val composeTestRule = createComposeRule()

  private lateinit var context: Context
  private lateinit var commonFinders: CommonFinders

  @Before
  fun before() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
    commonFinders = CommonFinders(
      composeTestRule = composeTestRule,
      context = context,
    )
  }

  @Test
  fun withoutNetworkFailures_assertCountryListItemsAreDisplayed() {
    val items = SampleData.CountryListItems.All

    declare<ObserveCountryListItems> {
      SampleDataObserveCountryListItems(data = items)
    }
    declare<RefreshCountryListItems> {
      SuccessfulRefreshCountryListItems(emitDelayInMillis = 100L)
    }

    composeTestRule.setContent {
      TestContent()
    }

    // Only doing just to make sure that all the potential network errors
    //  are collected and emitted properly to the Composable (which should not
    //  happen in this case).
    Thread.sleep(300L)

    items.forEach { item ->
      composeTestRule
        .onNodeWithText(text = item.officialName)
        .assertIsDisplayed()
    }

    commonFinders
      .findSnackbar()
      .assertIsNotDisplayed()
  }

  @Test
  fun requestRefreshFromDropdownMenuAction() {
    declare<ObserveCountryListItems> {
      SampleDataObserveCountryListItems()
    }
    declare<RefreshCountryListItems> {
      SuccessfulRefreshCountryListItems(emitDelayInMillis = 100L)
    }

    composeTestRule.setContent {
      TestContent()
    }

    Thread.sleep(300L)

    commonFinders
      .findMoreOptionsButton()
      .performClick()

    composeTestRule
      .onNodeWithText(
        text = context.getString(DesignR.string.refresh_item_label),
      )
      .performClick()

    commonFinders
      .findPullToRefreshIndicator()
      .assertIsDisplayed()
  }

  @Test
  fun requestRefreshFromSwipeDown() {
    declare<ObserveCountryListItems> {
      SampleDataObserveCountryListItems()
    }
    declare<RefreshCountryListItems> {
      SuccessfulRefreshCountryListItems(emitDelayInMillis = 100L)
    }

    composeTestRule.setContent {
      TestContent()
    }

    Thread.sleep(300L)

    swipeToRefresh()

    commonFinders
      .findPullToRefreshIndicator()
      .assertIsDisplayed()
  }

  @Test
  fun withNetworkFailure_assertSnackbarIsDisplayed() {
    declare<ObserveCountryListItems> {
      SampleDataObserveCountryListItems()
    }
    declare<RefreshCountryListItems> {
      FailingRefreshCountryListItems()
    }

    composeTestRule.setContent {
      TestContent()
    }

    commonFinders
      .findSnackbar()
      .assertIsDisplayed()
  }

  @Test
  fun requestRefreshFromNetworkErrorSnackbarAction() {
    declare<ObserveCountryListItems> {
      SampleDataObserveCountryListItems()
    }
    declare<RefreshCountryListItems> {
      FailingRefreshCountryListItems()
    }

    composeTestRule.setContent {
      TestContent()
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

  @Composable
  private fun TestContent(
    modifier: Modifier = Modifier,
  ) {
    RestCountriesTheme {
      CountriesScreen(
        onNavigateToCountryDetails = {},
        modifier = modifier,
      )
    }
  }

  private fun swipeToRefresh() {
    composeTestRule
      .onNode(hasScrollToIndexAction())
      .performTouchInput { swipeDown() }
  }
}
