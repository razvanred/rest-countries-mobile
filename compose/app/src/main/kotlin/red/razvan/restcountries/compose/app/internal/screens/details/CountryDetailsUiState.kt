// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal.screens.details

import androidx.compose.runtime.Immutable
import red.razvan.restcountries.data.models.DetailedCountry
import red.razvan.restcountries.data.models.NetworkFailure

@Immutable
data class CountryDetailsUiState(
  val country: DetailedCountry?,
  val isRefreshing: Boolean,
  val isDropdownMenuExpanded: Boolean,
  val networkFailure: NetworkFailure?,
) {
  companion object {
    val Empty = CountryDetailsUiState(
      country = null,
      isRefreshing = false,
      isDropdownMenuExpanded = false,
      networkFailure = null,
    )
  }
}
