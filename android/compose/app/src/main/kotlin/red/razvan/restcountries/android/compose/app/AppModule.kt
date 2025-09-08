// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import red.razvan.restcountries.android.compose.app.internal.screens.countries.CountriesScreenViewModel
import red.razvan.restcountries.android.compose.app.internal.screens.details.CountryDetailsScreenViewModel
import red.razvan.restcountries.android.compose.app.internal.screens.licenses.LicensesViewModel
import red.razvan.restcountries.android.domain.AndroidDomainModule
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.domain.DomainModule

val AppModule = module {
  includes(DomainModule, AndroidDomainModule)

  viewModel {
    CountriesScreenViewModel(
      observeCountryListItems = get(),
      refreshCountryListItems = get(),
    )
  }

  viewModel { parameters ->
    CountryDetailsScreenViewModel(
      countryId = parameters.get<CountryId>(),
      observeDetailedCountryByIdOrNull = get(),
      refreshDetailedCountryById = get(),
    )
  }

  viewModel {
    LicensesViewModel(
      observeArtifacts = get(),
    )
  }
}
