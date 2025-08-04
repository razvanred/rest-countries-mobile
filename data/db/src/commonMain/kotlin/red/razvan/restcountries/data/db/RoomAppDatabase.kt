// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters

@Database(
  entities = [
    CountryHeader::class,
    CountryDetails::class,
    Currency::class,
    CountryHeaderCurrencyCrossRef::class,
    Language::class,
    CountryHeaderLanguageCrossRef::class,
    CountryHeaderCapitalCrossRef::class,
    CountryHeaderContinentCrossRef::class,
    Continent::class,
    Capital::class,
  ],
  version = 1,
  exportSchema = false,
)
@ConstructedBy(RoomAppDatabaseConstructor::class)
@TypeConverters(AllTypeConverters::class)
abstract class RoomAppDatabase : RoomDatabase() {

  abstract val countryHeaderDao: CountryHeaderDao
  abstract val currencyDao: CurrencyDao
  abstract val countryHeaderCurrencyCrossRefDao: CountryHeaderCurrencyCrossRefDao
  abstract val languageDao: LanguageDao
  abstract val countryHeaderLanguageCrossRefDao: CountryHeaderLanguageCrossRefDao
  abstract val countryHeaderContinentCrossRefDao: CountryHeaderContinentCrossRefDao
  abstract val countryHeaderCapitalCrossRefDao: CountryHeaderCapitalCrossRefDao
  abstract val capitalDao: CapitalDao
  abstract val continentDao: ContinentDao
  abstract val countryDetailsDao: CountryDetailsDao
}

// The Room compiler generates the `actual` implementations.
expect object RoomAppDatabaseConstructor : RoomDatabaseConstructor<RoomAppDatabase> {
  override fun initialize(): RoomAppDatabase
}
