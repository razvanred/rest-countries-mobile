// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.domain

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val AndroidDomainModule = module {
  single<ObserveArtifacts> {
    DefaultObserveArtifacts(
      dispatchers = get(),
      context = androidContext(),
    )
  }
}
