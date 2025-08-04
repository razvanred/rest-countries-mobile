// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import org.koin.dsl.module

val DataDbModule = module {
  includes(PlatformModule)

  single {
    get<RoomAppDatabase>().countryHeaderDao
  }

  single {
    get<RoomAppDatabase>().currencyDao
  }

  single {
    get<RoomAppDatabase>().countryHeaderCurrencyCrossRefDao
  }

  single {
    get<RoomAppDatabase>().languageDao
  }

  single {
    get<RoomAppDatabase>().countryHeaderLanguageCrossRefDao
  }

  single {
    get<RoomAppDatabase>().countryHeaderCapitalCrossRefDao
  }

  single {
    get<RoomAppDatabase>().capitalDao
  }

  single {
    get<RoomAppDatabase>().continentDao
  }

  single {
    get<RoomAppDatabase>().countryHeaderContinentCrossRefDao
  }

  single<RunDatabaseTransaction> {
    RunRoomDatabaseTransaction(
      database = get(),
    )
  }

  single {
    get<RoomAppDatabase>().countryDetailsDao
  }
}
