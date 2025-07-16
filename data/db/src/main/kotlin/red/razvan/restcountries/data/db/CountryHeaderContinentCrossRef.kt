// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
  tableName = "country_continent_cross_ref",
  primaryKeys = ["country_header_id", "continent_name"],
  foreignKeys = [
    ForeignKey(
      entity = CountryHeader::class,
      parentColumns = ["id"],
      childColumns = ["country_header_id"],
      onDelete = CASCADE,
      onUpdate = CASCADE,
    ),
    ForeignKey(
      entity = Continent::class,
      parentColumns = ["name"],
      childColumns = ["continent_name"],
      onDelete = CASCADE,
      onUpdate = CASCADE,
    ),
  ],
)
data class CountryHeaderContinentCrossRef(
  @ColumnInfo(name = "country_header_id")
  val countryId: red.razvan.restcountries.data.db.CountryId,
  @ColumnInfo(name = "continent_name")
  val continentName: String,
)
