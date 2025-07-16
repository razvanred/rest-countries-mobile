// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import org.koin.dsl.module
import red.razvan.restcountries.data.repository.DataRepositoryModule

val DomainModule = module {
  includes(DataRepositoryModule)

  single<ObserveDetailedCountryByIdOrNull> {
    DefaultObserveDetailedCountryByIdOrNull(
      repository = get(),
    )
  }

  single<ObserveCountryListItems> {
    DefaultObserveCountryListItems(
      repository = get(),
    )
  }

  single<RefreshDetailedCountryById> {
    DefaultRefreshDetailedCountryById(
      repository = get(),
    )
  }

  single<RefreshCountryListItems> {
    DefaultRefreshCountryListItems(
      repository = get(),
    )
  }
}
