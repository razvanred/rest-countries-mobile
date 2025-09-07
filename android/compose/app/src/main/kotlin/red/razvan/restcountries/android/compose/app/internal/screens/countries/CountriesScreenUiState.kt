// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.countries

import androidx.compose.runtime.Immutable
import red.razvan.restcountries.data.models.CountryListItem
import red.razvan.restcountries.data.models.NetworkFailure

@Immutable
internal data class CountriesScreenUiState(
  val items: List<CountryListItem>,
  val isRefreshing: Boolean,
  val isDropdownMenuExpanded: Boolean,
  val networkFailure: NetworkFailure?,
) {
  companion object {
    val Empty = CountriesScreenUiState(
      items = emptyList(),
      isRefreshing = false,
      networkFailure = null,
      isDropdownMenuExpanded = false,
    )
  }
}
