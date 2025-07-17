// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
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
import red.razvan.restcountries.compose.app.AppModule
import red.razvan.restcountries.compose.app.R
import red.razvan.restcountries.compose.app.internal.screens.countries.CountriesScreen
import red.razvan.restcountries.compose.design.RestCountriesTheme
import red.razvan.restcountries.domain.ObserveCountryListItems
import red.razvan.restcountries.domain.RefreshCountryListItems
import red.razvan.restcountries.testresources.android.koin.KoinTestRule
import red.razvan.restcountries.testresources.domain.FailingRefreshCountryListItems
import red.razvan.restcountries.testresources.domain.SampleData
import red.razvan.restcountries.testresources.domain.SampleDataObserveCountryListItems
import red.razvan.restcountries.testresources.domain.SuccessfulRefreshCountryListItems
import red.razvan.restcountries.compose.design.R as DesignR

@RunWith(AndroidJUnit4::class)
class CountriesScreenTests : KoinTest {

  @get:Rule(order = 0)
  val koinTestRule = KoinTestRule(modules = listOf(AppModule))

  @get:Rule(order = 1)
  val composeTestRule = createComposeRule()

  lateinit var context: Context

  @Before
  fun before() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
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

    findSnackbar()
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

    composeTestRule
      .onNodeWithContentDescription(
        label = context.getString(DesignR.string.more_options_button_cd),
      )
      .performClick()

    composeTestRule
      .onNodeWithText(
        text = context.getString(DesignR.string.refresh_item_label),
      )
      .performClick()

    findPullToRefreshIndicatorNode()
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

    composeTestRule
      .onNode(hasScrollToIndexAction())
      .performTouchInput { swipeDown() }

    findPullToRefreshIndicatorNode()
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

    findSnackbar()
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

    findSnackbar()
      .assertIsNotDisplayed()

    findPullToRefreshIndicatorNode()
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

  private fun findPullToRefreshIndicatorNode() = composeTestRule
    .onNodeWithTag("pull-to-refresh-indicator")

  private fun findSnackbar() = composeTestRule
    .onNodeWithTag("snackbar")
}
