// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import kotlinx.coroutines.flow.Flow
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.NetworkFailure
import red.razvan.restcountries.data.stores.DetailedCountryStore

fun interface RefreshDetailedCountryById {
  operator fun invoke(id: CountryId): Flow<InvokeStatus<Unit, NetworkFailure>>
}

internal class DefaultRefreshDetailedCountryById(
  private val store: DetailedCountryStore,
) : RefreshDetailedCountryById {

  override fun invoke(id: CountryId): Flow<InvokeStatus<Unit, NetworkFailure>> = store
    .streamRefreshStatus(id)
}
