// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.testresources.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.DetailedCountry
import red.razvan.restcountries.domain.ObserveDetailedCountryByIdOrNull
import red.razvan.restcountries.testresources.domain.SampleData.DetailedCountries

class SampleDataObserveDetailedCountryByIdOrNull(
  private val resultEmitDelayInMillis: Long = 0L,
) : ObserveDetailedCountryByIdOrNull {
  override fun invoke(id: CountryId): Flow<DetailedCountry?> = flow {
    delay(resultEmitDelayInMillis)
    emit(DetailedCountries.All[id])
  }
}
