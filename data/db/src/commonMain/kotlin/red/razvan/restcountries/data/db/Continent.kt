// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "continent")
data class Continent(
  @PrimaryKey
  val name: String,
)
