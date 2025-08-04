// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import org.koin.dsl.module

internal actual val PlatformModule = module {
  single {
    AppleRoomAppDatabase
      .databaseBuilder()
      .build()
  }
}
