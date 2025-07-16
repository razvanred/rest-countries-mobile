// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import kotlinx.coroutines.flow.Flow
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.DetailedCountry
import red.razvan.restcountries.data.repository.CountryRepository

fun interface ObserveDetailedCountryByIdOrNull {
  operator fun invoke(id: CountryId): Flow<DetailedCountry?>
}

internal class DefaultObserveDetailedCountryByIdOrNull(
  private val repository: CountryRepository,
) : ObserveDetailedCountryByIdOrNull {

  override fun invoke(id: CountryId): Flow<DetailedCountry?> = repository.observeDetailsByIdOrNull(id = id)
}
