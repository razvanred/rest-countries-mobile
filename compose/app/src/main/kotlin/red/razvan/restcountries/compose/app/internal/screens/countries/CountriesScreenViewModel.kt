// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal.screens.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import red.razvan.restcountries.data.models.NetworkFailure
import red.razvan.restcountries.domain.InvokeStatus
import red.razvan.restcountries.domain.ObserveCountryListItems
import red.razvan.restcountries.domain.RefreshCountryListItems

internal class CountriesScreenViewModel(
  observeCountryListItems: ObserveCountryListItems,
  private val refreshCountryListItems: RefreshCountryListItems,
) : ViewModel() {

  private val countries = observeCountryListItems()
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(500),
      initialValue = emptyList(),
    )

  private val mutableIsRefreshing = MutableStateFlow(false)
  private val isRefreshing = mutableIsRefreshing.asStateFlow()

  private val mutableNetworkFailure = MutableStateFlow<NetworkFailure?>(null)
  private val networkFailure = mutableNetworkFailure.asStateFlow()

  private val mutableIsDropdownMenuExpanded = MutableStateFlow(false)
  private val isDropdownMenuExpanded = mutableIsDropdownMenuExpanded.asStateFlow()

  val state = combine(
    countries,
    isRefreshing,
    networkFailure,
    isDropdownMenuExpanded,
  ) { countries, isRefreshing, networkFailure, isDropdownMenuExpanded ->
    CountriesScreenUiState(
      items = countries,
      isRefreshing = isRefreshing,
      networkFailure = networkFailure,
      isDropdownMenuExpanded = isDropdownMenuExpanded,
    )
  }

  init {
    refresh()
  }

  fun refresh() {
    viewModelScope.launch {
      mutableNetworkFailure.emit(null)

      refreshCountryListItems()
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
