// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_header")
data class CountryHeader(
  @PrimaryKey
  val id: CountryId,
  @ColumnInfo(name = "common_name")
  val commonName: String,
  @ColumnInfo(name = "official_name")
  val officialName: String,
  @ColumnInfo(name = "emoji_flag")
  val emojiFlag: String,
)
