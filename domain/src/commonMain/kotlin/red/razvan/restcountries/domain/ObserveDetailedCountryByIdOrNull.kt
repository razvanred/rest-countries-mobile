// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.DetailedCountry
import red.razvan.restcountries.data.stores.DetailedCountryStore

fun interface ObserveDetailedCountryByIdOrNull {
  operator fun invoke(id: CountryId): Flow<DetailedCountry?>
}

internal class DefaultObserveDetailedCountryByIdOrNull(
  private val store: DetailedCountryStore,
) : ObserveDetailedCountryByIdOrNull {

  override fun invoke(id: CountryId): Flow<DetailedCountry?> = store
    .streamRequiredData(request = StoreReadRequest.cached(id, refresh = false))
}
