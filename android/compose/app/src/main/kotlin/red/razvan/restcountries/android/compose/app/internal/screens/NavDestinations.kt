// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens

import kotlinx.serialization.Serializable
import red.razvan.restcountries.data.models.CountryId

internal sealed interface NavDestination

internal object NavDestinations {
  @Serializable
  data object CountriesScreen : NavDestination

  // Apparently Navigation only supports flat types
  @Serializable
  @JvmInline
  value class CountryDetailsScreen(
    private val countryIdRawValue: String,
  ) : NavDestination {

    val countryId: CountryId
      get() = CountryId(value = countryIdRawValue)

    companion object {
      fun CountryId.toDestination(): CountryDetailsScreen = CountryDetailsScreen(value)
    }
  }
}
