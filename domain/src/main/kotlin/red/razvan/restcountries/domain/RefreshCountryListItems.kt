// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import kotlinx.coroutines.flow.Flow
import red.razvan.restcountries.data.models.InvokeStatus
import red.razvan.restcountries.data.models.NetworkFailure
import red.razvan.restcountries.data.repository.CountryRepository

fun interface RefreshCountryListItems {
  operator fun invoke(): Flow<InvokeStatus<Unit, NetworkFailure>>
}

internal class DefaultRefreshCountryListItems(
  private val repository: CountryRepository,
) : RefreshCountryListItems {

  override fun invoke(): Flow<InvokeStatus<Unit, NetworkFailure>> = repository.refreshListItems()
}
