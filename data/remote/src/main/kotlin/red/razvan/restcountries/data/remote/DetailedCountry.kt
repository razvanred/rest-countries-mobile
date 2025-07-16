// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailedCountry(
  val cca3: String,
  val name: CountryName,
  @SerialName("flag")
  val emojiFlag: String,
  @SerialName("flags")
  val flag: CountryFlag,
  val capital: List<String> = emptyList(),
  val continents: List<String>,
  val currencies: Map<String, Currency> = emptyMap(),
  val languages: Map<String, String> = emptyMap(),
  val population: UInt,
)
