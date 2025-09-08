// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.licenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import red.razvan.restcountries.android.domain.ObserveArtifacts

internal class LicensesViewModel(
  observeArtifacts: ObserveArtifacts,
) : ViewModel() {

  val state = observeArtifacts()
    .map { artifacts ->
      LicensesUiState(
        artifacts = artifacts,
      )
    }
    .stateIn(
      scope = viewModelScope,
      initialValue = LicensesUiState.Empty,
      started = SharingStarted.WhileSubscribed(5_000),
    )
}
