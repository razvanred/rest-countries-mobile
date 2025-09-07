// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.details

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
import red.razvan.restcountries.android.compose.app.AppModule
import red.razvan.restcountries.data.models.NetworkFailure
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
  fun `Given a successful refresh with cached data, check the UI state`() = runTest {
    val expectedCountry = DetailedCountries.Italy

    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull(resultEmitDelayInMillis = 100L)
    }
    declare<RefreshDetailedCountryById> {
      SuccessfulRefreshDetailedCountryById(emitDelayInMillis = 200L)
    }

    val expectedInitialState = CountryDetailsUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true, country = expectedCountry)
    val expectedRefreshedState = expectedRefreshingState
      .copy(isRefreshing = false)

    get<CountryDetailsScreenViewModel> { parametersOf(expectedCountry.id) }
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshedState)
      }
  }

  @Test
  fun `Given a successful refresh without cached data, check the UI state`() = runTest {
    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull()
    }
    declare<RefreshDetailedCountryById> {
      SuccessfulRefreshDetailedCountryById(emitDelayInMillis = 200L)
    }

    val expectedInitialState = CountryDetailsUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedRefreshedState = expectedRefreshingState
      .copy(
        isRefreshing = false,
      )

    get<CountryDetailsScreenViewModel> { parametersOf(DetailedCountries.UnknownCountryId) }
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshedState)
      }
  }

  @Test
  fun `Given a failing refresh with cached data, check the UI state`() = runTest {
    val expectedNetworkFailure = NetworkFailure.WithHttpStatusCode(
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
      .copy(isRefreshing = true, country = expectedCountry)
    val expectedRefreshedState = expectedRefreshingState
      .copy(
        networkFailure = expectedNetworkFailure,
        isRefreshing = false,
      )

    get<CountryDetailsScreenViewModel> { parametersOf(expectedCountry.id) }
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshedState)
      }
  }

  @Test
  fun `Given a failing refresh without cached data, check the UI state`() = runTest {
    val expectedNetworkFailure = NetworkFailure.Undefined(exception = RuntimeException("Test"))

    declare<ObserveDetailedCountryByIdOrNull> {
      SampleDataObserveDetailedCountryByIdOrNull()
    }
    declare<RefreshDetailedCountryById> {
      FailingRefreshDetainedCountryById(failure = expectedNetworkFailure, 200L)
    }

    val expectedInitialState = CountryDetailsUiState.Empty
    val expectedRefreshingState = expectedInitialState
      .copy(isRefreshing = true)
    val expectedRefreshedState = expectedRefreshingState
      .copy(
        isRefreshing = false,
        networkFailure = expectedNetworkFailure,
      )

    get<CountryDetailsScreenViewModel> { parametersOf(DetailedCountries.UnknownCountryId) }
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshingState)
        assertThat(awaitItem()).isEqualTo(expectedRefreshedState)
      }
  }
}
