// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.models

data class DetailedCountry(
  val id: CountryId,
  val officialName: String,
  val commonName: String,
  val emojiFlag: String,
  val flag: Flag,
  val currencies: List<Currency>,
  val capital: List<String>,
  val continents: List<String>,
  val languages: List<Language>,
) {
  val isOfficialNameDifferentFromCommon: Boolean
    get() = !officialName.equals(commonName, ignoreCase = true)

  data class Flag(
    val png: String,
    val svg: String,
    val contentDescription: String,
  )
}
