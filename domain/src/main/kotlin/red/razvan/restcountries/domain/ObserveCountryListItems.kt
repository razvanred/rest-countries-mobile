// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import kotlinx.coroutines.flow.Flow
import red.razvan.restcountries.data.models.CountryListItem
import red.razvan.restcountries.data.repository.CountryRepository

fun interface ObserveCountryListItems {
  operator fun invoke(): Flow<List<CountryListItem>>
}

internal class DefaultObserveCountryListItems(
  private val repository: CountryRepository,
) : ObserveCountryListItems {

  override fun invoke(): Flow<List<CountryListItem>> = repository.observeListItems()
}
