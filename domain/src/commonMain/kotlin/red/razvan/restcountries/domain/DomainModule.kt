// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import org.koin.dsl.module
import red.razvan.restcountries.data.stores.DataStoresModule

val DomainModule = module {
  includes(DataStoresModule)

  single<ObserveDetailedCountryByIdOrNull> {
    DefaultObserveDetailedCountryByIdOrNull(
      store = get(),
    )
  }

  single<ObserveCountryListItems> {
    DefaultObserveCountryListItems(
      store = get(),
    )
  }

  single<RefreshDetailedCountryById> {
    DefaultRefreshDetailedCountryById(
      store = get(),
    )
  }

  single<RefreshCountryListItems> {
    DefaultRefreshCountryListItems(
      store = get(),
    )
  }
}
