// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.TypeConverter

internal object AllTypeConverters {
  @TypeConverter
  fun stringToCountryId(value: String) = CountryId(value)

  @TypeConverter
  fun countryIdToString(id: CountryId) = id.value

  @TypeConverter
  fun stringToLanguageId(value: String) = LanguageId(value)

  @TypeConverter
  fun languageIdToString(id: LanguageId) = id.value

  @TypeConverter
  fun intToUInt(value: Int) = value.toUInt()

  @TypeConverter
  fun uIntToInt(value: UInt) = value.toInt()
}
