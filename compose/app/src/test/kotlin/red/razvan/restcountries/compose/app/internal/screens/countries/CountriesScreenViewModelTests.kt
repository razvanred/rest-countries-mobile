// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal.screens.countries

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declare
import red.razvan.restcountries.compose.app.AppModule
import red.razvan.restcountries.compose.app.internal.screens.countries.CountriesScreenUiState
import red.razvan.restcountries.compose.app.internal.screens.countries.CountriesScreenViewModel
import red.razvan.restcountries.data.models.NetworkFailures
import red.razvan.restcountries.domain.ObserveCountryListItems
import red.razvan.restcountries.domain.RefreshCountryListItems
import red.razvan.restcountries.testresources.domain.EmptyObserveCountryListItems
import red.razvan.restcountries.testresources.domain.FailingRefreshCountryListItems
import red.razvan.restcountries.testresources.domain.SampleData.CountryListItems
import red.razvan.restcountries.testresources.domain.SampleDataObserveCountryListItems
import red.razvan.restcountries.testresources.domain.SuccessfulRefreshCountryListItems

@OptIn(ExperimentalCoroutinesApi::class)
internal class CountriesScreenViewModelTests : KoinTest {

  companion object {
    @BeforeClass
    @JvmStatic
    fun configureBeforeAll() {
      Dispatchers.setMain(StandardTestDispatcher())
    }
  }

  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(AppModule)
  }

  private val viewModel: CountriesScreenViewModel by inject()

  @Test
  fun `Given a successful refresh and the cached data emitted beforehand, check the UI state`() = runTest {
    declare<ObserveCountryListItems> {
      SampleDataObserveCountryListItems(100L)
    }
    declare<RefreshCountryListItems> {
      SuccessfulRefreshCountryListItems(200L)
    }

    val expectedInitialState = CountriesScreenUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedRefreshingWithCachedDataState = expectedRefreshingState
      .copy(items = CountryListItems.All)
    val expectedLoadedState = expectedRefreshingWithCachedDataState
      .copy(isRefreshing = false)

    viewModel
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingWithCachedDataState)
        assertThat(awaitItem()).isEqualTo(expectedLoadedState)
      }
  }

  @Suppress("UnnecessaryVariable")
  @Test
  fun `Given a successful refresh and the data emitted later, check the UI state`() = runTest {
    declare<ObserveCountryListItems> {
      SampleDataObserveCountryListItems(200L)
    }
    declare<RefreshCountryListItems> {
      SuccessfulRefreshCountryListItems(100L)
    }

    val expectedInitialState = CountriesScreenUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedRefreshedWithoutCachedDataState = expectedInitialState
    val expectedLoadedState = expectedRefreshedWithoutCachedDataState
      .copy(items = CountryListItems.All)

    viewModel
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshedWithoutCachedDataState)
        assertThat(awaitItem()).isEqualTo(expectedLoadedState)
      }
  }

  @Test
  fun `Given a failing refresh and the cached data emitted, check the UI state`() = runTest {
    val expectedNetworkFailure = NetworkFailures.HttpStatusCodeFailureResult(
      code = 400,
      exception = RuntimeException("test"),
    )

    declare<ObserveCountryListItems> {
      SampleDataObserveCountryListItems()
    }
    declare<RefreshCountryListItems> {
      FailingRefreshCountryListItems(
        failure = expectedNetworkFailure,
        failureEmitDelayInMillis = 200L,
      )
    }

    val expectedInitialState = CountriesScreenUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedRefreshingWithCachedDataState = expectedRefreshingState
      .copy(items = CountryListItems.All)
    val expectedLoadedState = expectedRefreshingWithCachedDataState
      .copy(
        networkFailure = expectedNetworkFailure,
        isRefreshing = false,
      )

    viewModel
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingWithCachedDataState)
        awaitItem()
        assertThat(awaitItem()).isEqualTo(expectedLoadedState)
      }
  }

  @Test
  fun `Given a failing refresh without emitted cached data, check the UI state`() = runTest {
    val expectedNetworkFailure = NetworkFailures.HttpStatusCodeFailureResult(
      code = 400,
      exception = RuntimeException("test"),
    )

    declare<ObserveCountryListItems> {
      EmptyObserveCountryListItems()
    }
    declare<RefreshCountryListItems> {
      FailingRefreshCountryListItems(
        failure = expectedNetworkFailure,
        failureEmitDelayInMillis = 200L,
      )
    }

    val expectedInitialState = CountriesScreenUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedLoadedState = expectedRefreshingState
      .copy(
        isRefreshing = false,
        networkFailure = expectedNetworkFailure,
      )

    viewModel
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        awaitItem()
        assertThat(awaitItem()).isEqualTo(expectedLoadedState)
      }
  }
}
