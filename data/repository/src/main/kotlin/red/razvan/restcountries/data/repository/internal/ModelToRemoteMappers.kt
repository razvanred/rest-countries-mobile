// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.repository.internal

import red.razvan.restcountries.core.utils.Mapper
import red.razvan.restcountries.data.models.CountryId

internal interface CountryIdToRemoteMapper : Mapper<CountryId, String>

internal object DefaultCountryIdToRemoteMapper : CountryIdToRemoteMapper {

  override fun map(source: CountryId): String = source.value
}
