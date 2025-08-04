// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.StoreReadRequest
import red.razvan.restcountries.data.models.CountryListItem
import red.razvan.restcountries.data.stores.CountryListItemsStore

fun interface ObserveCountryListItems {
  operator fun invoke(): Flow<List<CountryListItem>>
}

internal class DefaultObserveCountryListItems(
  private val store: CountryListItemsStore,
) : ObserveCountryListItems {

  override fun invoke(): Flow<List<CountryListItem>> = store
    .streamRequiredData(request = StoreReadRequest.cached(Unit, refresh = false))
}
