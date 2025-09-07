// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.countries

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
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declare
import red.razvan.restcountries.android.compose.app.AppModule
import red.razvan.restcountries.data.models.NetworkFailure
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

    private val CachedCountries = CountryListItems.All

    private val MocksModule = module {
      single<ObserveCountryListItems> {
        SampleDataObserveCountryListItems(
          data = CachedCountries,
          emitDelayInMillis = 100L,
        )
      }
    }
  }

  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(AppModule, MocksModule)
  }

  private val viewModel: CountriesScreenViewModel by inject()

  @Test
  fun `Given a successful refresh with cached data, check the UI state`() = runTest {
    declare<RefreshCountryListItems> {
      SuccessfulRefreshCountryListItems(200L)
    }

    val expectedInitialState = CountriesScreenUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true, items = CachedCountries)
    val expectedRefreshedState = expectedRefreshingState
      .copy(isRefreshing = false)

    viewModel
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshedState)
      }
  }

  @Test
  fun `Given a successful refresh without cached data, check the UI state`() = runTest {
    declare<RefreshCountryListItems> {
      SuccessfulRefreshCountryListItems(200L)
    }
    declare<ObserveCountryListItems> {
      EmptyObserveCountryListItems()
    }

    val expectedInitialState = CountriesScreenUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedRefreshedState = expectedRefreshingState
      .copy(isRefreshing = false)

    viewModel
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshedState)
      }
  }

  @Test
  fun `Given a failing refresh and the cached data emitted, check the UI state`() = runTest {
    val expectedNetworkFailure = NetworkFailure.WithHttpStatusCode(
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
      .copy(isRefreshing = true, items = CachedCountries)
    val expectedRefreshedState = expectedRefreshingState
      .copy(
        networkFailure = expectedNetworkFailure,
        isRefreshing = false,
      )

    viewModel
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshedState)
      }
  }

  @Test
  fun `Given a failing refresh without cached data, check the UI state`() = runTest {
    val expectedNetworkFailure = NetworkFailure.WithHttpStatusCode(
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
    val expectedRefreshedState = expectedRefreshingState
      .copy(
        isRefreshing = false,
        networkFailure = expectedNetworkFailure,
      )

    viewModel
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshedState)
      }
  }
}
