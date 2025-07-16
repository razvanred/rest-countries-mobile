// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal.screens.details

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
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.declare
import red.razvan.restcountries.compose.app.AppModule
import red.razvan.restcountries.compose.app.internal.screens.details.CountryDetailsScreenViewModel
import red.razvan.restcountries.compose.app.internal.screens.details.CountryDetailsUiState
import red.razvan.restcountries.data.models.NetworkFailures
import red.razvan.restcountries.domain.ObserveDetailedCountryByIdOrNull
import red.razvan.restcountries.domain.RefreshDetailedCountryById
import red.razvan.restcountries.testresources.domain.FailingRefreshDetainedCountryById
import red.razvan.restcountries.testresources.domain.SampleData.DetailedCountries
import red.razvan.restcountries.testresources.domain.SampleDataObserveDetailedCountryByIdOrNull
import red.razvan.restcountries.testresources.domain.SuccessfulRefreshDetailedCountryById

@OptIn(ExperimentalCoroutinesApi::class)
class CountryDetailsScreenViewModelTests : KoinTest {

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

  @Test
  fun `Given a successful refresh and the cached data emitted beforehand, check the UI state`() = runTest {
    val expectedCountry = DetailedCountries.Italy

    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull(resultEmitDelayInMillis = 100L)
    }
    declare<RefreshDetailedCountryById> {
      SuccessfulRefreshDetailedCountryById(emitDelayInMillis = 200L)
    }

    val expectedInitialState = CountryDetailsUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedRefreshingWithCachedDataState = expectedRefreshingState
      .copy(country = expectedCountry)
    val expectedLoadedState = expectedRefreshingWithCachedDataState
      .copy(isRefreshing = false)

    get<CountryDetailsScreenViewModel> { parametersOf(expectedCountry.id) }
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingWithCachedDataState)
        assertThat(awaitItem()).isEqualTo(expectedLoadedState)
      }
  }

  @Test
  fun `Given a successful refresh and the cached data emitted later, check the UI state`() = runTest {
    val expectedCountry = DetailedCountries.Italy

    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull(resultEmitDelayInMillis = 300L)
    }
    declare<RefreshDetailedCountryById> {
      SuccessfulRefreshDetailedCountryById(emitDelayInMillis = 100L)
    }

    val expectedInitialState = CountryDetailsUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedRefreshedWithoutCachedDataState = expectedRefreshingState
      .copy(isRefreshing = false)
    val loadedState = expectedRefreshedWithoutCachedDataState
      .copy(country = expectedCountry)

    get<CountryDetailsScreenViewModel> { parametersOf(expectedCountry.id) }
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshedWithoutCachedDataState)
        assertThat(awaitItem()).isEqualTo(loadedState)
      }
  }

  @Test
  fun `Given a failing refresh and the cached data emitted, check the UI state`() = runTest {
    val expectedNetworkFailure = NetworkFailures.HttpStatusCodeFailureResult(
      code = 400,
      exception = RuntimeException("test"),
    )
    val expectedCountry = DetailedCountries.Italy

    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull(200L)
    }
    declare<RefreshDetailedCountryById> {
      FailingRefreshDetainedCountryById(
        failure = expectedNetworkFailure,
        failureEmitDelayInMillis = 300L,
      )
    }

    val expectedInitialState = CountryDetailsUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedRefreshingWithCachedDataState = expectedRefreshingState
      .copy(
        country = expectedCountry,
      )
    val expectedLoadedState = expectedRefreshingWithCachedDataState
      .copy(
        networkFailure = expectedNetworkFailure,
        isRefreshing = false,
      )

    get<CountryDetailsScreenViewModel> { parametersOf(expectedCountry.id) }
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
    val expectedNetworkFailure = NetworkFailures.Undefined(exception = RuntimeException("Test"))

    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull()
    }
    declare<RefreshDetailedCountryById> {
      FailingRefreshDetainedCountryById(failure = expectedNetworkFailure, 200L)
    }

    val expectedInitialState = CountryDetailsUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedLoadedState = expectedRefreshingState
      .copy(
        isRefreshing = false,
        networkFailure = expectedNetworkFailure,
      )

    get<CountryDetailsScreenViewModel> { parametersOf(DetailedCountries.UnknownCountryId) }
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        awaitItem()
        assertThat(awaitItem()).isEqualTo(expectedLoadedState)
      }
  }
}
