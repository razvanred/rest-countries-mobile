// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
  tableName = "country_header_language_cross_ref",
  primaryKeys = ["country_header_id", "language_id"],
  foreignKeys = [
    ForeignKey(
      entity = CountryHeader::class,
      parentColumns = ["id"],
      childColumns = ["country_header_id"],
      onDelete = CASCADE,
      onUpdate = CASCADE,
    ),
    ForeignKey(
      entity = Language::class,
      parentColumns = ["id"],
      childColumns = ["language_id"],
      onDelete = CASCADE,
      onUpdate = CASCADE,
    ),
  ],
)
data class CountryHeaderLanguageCrossRef(
  @ColumnInfo(name = "country_header_id")
  val countryId: red.razvan.restcountries.data.db.CountryId,
  @ColumnInfo(name = "language_id")
  val languageId: LanguageId,
)
