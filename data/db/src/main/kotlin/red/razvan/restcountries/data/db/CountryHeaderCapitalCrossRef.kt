// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
  tableName = "country_capital_cross_ref",
  primaryKeys = ["country_header_id", "capital_name"],
  foreignKeys = [
    ForeignKey(
      entity = CountryHeader::class,
      parentColumns = ["id"],
      childColumns = ["country_header_id"],
      onDelete = ForeignKey.Companion.CASCADE,
      onUpdate = ForeignKey.Companion.CASCADE,
    ),
    ForeignKey(
      entity = Capital::class,
      parentColumns = ["name"],
      childColumns = ["capital_name"],
      onDelete = ForeignKey.Companion.CASCADE,
      onUpdate = ForeignKey.Companion.CASCADE,
    ),
  ],
)
data class CountryHeaderCapitalCrossRef(
  @ColumnInfo(name = "country_header_id")
  val countryId: CountryId,
  @ColumnInfo(name = "capital_name")
  val capitalName: String,
)
