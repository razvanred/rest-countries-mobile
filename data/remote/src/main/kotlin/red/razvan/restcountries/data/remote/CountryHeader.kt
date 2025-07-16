// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryHeader(
  val cca3: String,
  val name: CountryName,
  @SerialName("flag")
  val emojiFlag: String,
)
