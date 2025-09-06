// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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

  private val mutableIsRefreshing = MutableStateFlow(false)
  private val isRefreshing = mutableIsRefreshing.asStateFlow()

  private val mutableNetworkFailure = MutableStateFlow<NetworkFailure?>(null)
  private val networkFailure = mutableNetworkFailure.asStateFlow()

  private val mutableIsDropdownMenuExpanded = MutableStateFlow(false)
  private val isDropdownMenuExpanded = mutableIsDropdownMenuExpanded.asStateFlow()

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
      mutableNetworkFailure.emit(null)

      refreshDetailedCountryById(countryId)
        .collect { status ->
          when (status) {
            is InvokeStatus.Failure<NetworkFailure> -> {
              mutableNetworkFailure.emit(status.error)
              mutableIsRefreshing.emit(false)
            }
            InvokeStatus.InProgress -> {
              mutableIsRefreshing.emit(true)
            }
            is InvokeStatus.Successful<*> -> {
              mutableIsRefreshing.emit(false)
            }
          }
        }
    }
  }

  fun clearNetworkFailure() {
    viewModelScope.launch {
      mutableNetworkFailure.emit(null)
    }
  }

  fun setDropdownMenuExpanded(isExpanded: Boolean) {
    mutableIsDropdownMenuExpanded.value = isExpanded
  }
}
