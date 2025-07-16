// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.testresources.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import red.razvan.restcountries.data.models.CountryListItem
import red.razvan.restcountries.domain.ObserveCountryListItems
import red.razvan.restcountries.testresources.domain.SampleData.CountryListItems

class SampleDataObserveCountryListItems(
  private val emitDelayInMillis: Long = 0L,
  private val data: List<CountryListItem> = CountryListItems.All,
) : ObserveCountryListItems {

  override fun invoke(): Flow<List<CountryListItem>> = flow {
    delay(emitDelayInMillis)
    emit(data)
  }
}

class EmptyObserveCountryListItems(
  private val emitDelayInMillis: Long = 0L,
) : ObserveCountryListItems by SampleDataObserveCountryListItems(
  emitDelayInMillis = emitDelayInMillis,
  data = emptyList(),
)
