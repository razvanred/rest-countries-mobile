// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import red.razvan.restcountries.data.db.TriggerQueries.addTriggerQueries

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

  companion object {
    fun inMemoryDatabaseBuilder(context: Context): Builder<RoomAppDatabase> = Room
      .inMemoryDatabaseBuilder(context, RoomAppDatabase::class.java)
      .addTriggerQueries()

    fun databaseBuilder(context: Context): Builder<RoomAppDatabase> = Room
      .databaseBuilder(context, RoomAppDatabase::class.java, "app.db")
      .addTriggerQueries()
  }
}
