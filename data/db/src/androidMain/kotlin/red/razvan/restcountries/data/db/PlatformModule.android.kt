// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual val PlatformModule = module {
  single {
    AndroidRoomAppDatabase
      .databaseBuilder(androidContext())
      .build()
  }
}
