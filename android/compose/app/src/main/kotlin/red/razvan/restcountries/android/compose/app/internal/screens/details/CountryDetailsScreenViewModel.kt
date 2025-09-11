// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.NetworkFailure
import red.razvan.restcountries.domain.InvokeStatus
import red.razvan.restcountries.domain.ObserveDetailedCountryByIdOrNull
import red.razvan.restcountries.domain.RefreshDetailedCountryById

internal class CountryDetailsScreenViewModel(
  private val countryId: CountryId,
  observeDetailedCountryByIdOrNull: ObserveDetailedCountryByIdOrNull,
  private val refreshDetailedCountryById: RefreshDetailedCountryById,
) : ViewModel() {

  private val isRefreshing = MutableStateFlow(false)

  private val networkFailure = MutableStateFlow<NetworkFailure?>(null)

  private val isDropdownMenuExpanded = MutableStateFlow(false)

  val state = combine(
    observeDetailedCountryByIdOrNull(countryId),
    isRefreshing,
    networkFailure,
    isDropdownMenuExpanded,
  ) { country, isRefreshing, networkFailure, isDropdownMenuExpanded ->
    CountryDetailsUiState(
      country = country,
      isRefreshing = isRefreshing,
      networkFailure = networkFailure,
      isDropdownMenuExpanded = isDropdownMenuExpanded,
    )
  }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = CountryDetailsUiState.Empty,
    )

  init {
    refresh()
  }

  fun refresh() {
    viewModelScope.launch {
      networkFailure.emit(null)

      refreshDetailedCountryById(countryId)
        .collect { status ->
          when (status) {
            is InvokeStatus.Failure<NetworkFailure> -> {
              networkFailure.emit(status.error)
              isRefreshing.emit(false)
            }
            InvokeStatus.InProgress -> {
              isRefreshing.emit(true)
            }
            is InvokeStatus.Successful<*> -> {
              isRefreshing.emit(false)
            }
          }
        }
    }
  }

  fun clearNetworkFailure() {
    viewModelScope.launch {
      networkFailure.emit(null)
    }
  }

  fun setDropdownMenuExpanded(isExpanded: Boolean) {
    isDropdownMenuExpanded.value = isExpanded
  }
}
