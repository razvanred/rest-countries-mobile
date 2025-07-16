// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.repository.internal

import red.razvan.restcountries.core.utils.Mapper
import red.razvan.restcountries.data.models.CountryId

internal interface CountryIdToDbCountryIdMapper : Mapper<CountryId, DbCountryId>

internal object DefaultCountryIdToDbCountryIdMapper : CountryIdToDbCountryIdMapper {

  override fun map(source: CountryId): DbCountryId = with(source) {
    DbCountryId(value)
  }
}
