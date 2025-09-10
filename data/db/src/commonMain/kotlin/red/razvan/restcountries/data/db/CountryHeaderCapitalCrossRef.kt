// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
  tableName = "country_header_capital_cross_ref",
  primaryKeys = ["country_header_id", "capital_name"],
  foreignKeys = [
    ForeignKey(
      entity = CountryHeader::class,
      parentColumns = ["id"],
      childColumns = ["country_header_id"],
      onDelete = CASCADE,
      onUpdate = CASCADE,
    ),
    ForeignKey(
      entity = Capital::class,
      parentColumns = ["name"],
      childColumns = ["capital_name"],
      onDelete = CASCADE,
      onUpdate = CASCADE,
    ),
  ],
  indices = [
    Index(
      name = "index_country_header_capital_cross_ref_capital_name",
      value = ["capital_name"],
    ),
  ],
)
data class CountryHeaderCapitalCrossRef(
  @ColumnInfo(name = "country_header_id")
  val countryHeaderId: CountryId,
  @ColumnInfo(name = "capital_name")
  val capitalName: String,
)
