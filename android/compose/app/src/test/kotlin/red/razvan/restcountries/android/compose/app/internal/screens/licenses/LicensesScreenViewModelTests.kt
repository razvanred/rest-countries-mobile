// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.licenses

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
import red.razvan.restcountries.android.compose.app.AppModule
import red.razvan.restcountries.android.domain.ObserveArtifacts
import red.razvan.restcountries.testresources.android.domain.SampleData
import red.razvan.restcountries.testresources.android.domain.SampleDataObserveArtifacts

@OptIn(ExperimentalCoroutinesApi::class)
class LicensesScreenViewModelTests : KoinTest {

  companion object {
    @BeforeClass
    @JvmStatic
    fun configureBeforeAll() {
      Dispatchers.setMain(StandardTestDispatcher())
    }

    private val Artifacts = SampleData.Artifacts.All

    private val MocksModule = module {
      single<ObserveArtifacts> {
        SampleDataObserveArtifacts(
          data = Artifacts,
          emitDelayInMillis = 100L,
        )
      }
    }
  }

  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(AppModule, MocksModule)
  }

  private val viewModel: LicensesViewModel by inject()

  @Test
  fun `Check artifacts are emitted`() = runTest {
    val expectedInitialState = LicensesUiState.Empty
    val expectedLoadedState = expectedInitialState
      .copy(artifacts = Artifacts)

    viewModel
      .state
      .test {
        assertThat(awaitItem()).isEqualTo(expectedInitialState)
        assertThat(awaitItem()).isEqualTo(expectedLoadedState)
      }
  }
}
