// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
  tableName = "country_details",
  foreignKeys = [
    ForeignKey(
      entity = CountryHeader::class,
      parentColumns = ["id"],
      childColumns = ["id"],
      onDelete = CASCADE,
      onUpdate = CASCADE,
    ),
  ],
)
data class CountryDetails(
  @PrimaryKey
  val id: CountryId,
  @ColumnInfo(name = "svg_flag")
  val svgFlag: String,
  @ColumnInfo(name = "png_flag")
  val pngFlag: String,
  @ColumnInfo(name = "flag_content_description")
  val flagContentDescription: String,
  val population: UInt,
)
