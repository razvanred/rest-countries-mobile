// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

object KoinDomainHelper : KoinComponent {

  val observeCountryListItems: ObserveCountryListItems get() = get()
  val observeDetailedCountryByIdOrNull: ObserveDetailedCountryByIdOrNull get() = get()
  val refreshCountryListItems: RefreshCountryListItems get() = get()
  val refreshDetailedCountryById: RefreshDetailedCountryById get() = get()

  fun startKoin() {
    startKoin {
      modules(DomainModule)
    }
  }
}
