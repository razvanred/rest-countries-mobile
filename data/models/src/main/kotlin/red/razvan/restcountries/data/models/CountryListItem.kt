// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.models

data class CountryListItem(
  val id: CountryId,
  val officialName: String,
  val emojiFlag: String,
)
