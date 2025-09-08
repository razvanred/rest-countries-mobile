// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.licenses

import androidx.compose.runtime.Immutable
import red.razvan.restcountries.android.domain.ArtifactsGroup

@Immutable
data class LicensesUiState(
  val artifacts: List<ArtifactsGroup>,
) {
  companion object {
    val Empty = LicensesUiState(
      artifacts = emptyList(),
    )
  }
}
