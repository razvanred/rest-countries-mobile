// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.testresources.domain

import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.CountryListItem
import red.razvan.restcountries.data.models.Currency
import red.razvan.restcountries.data.models.CurrencyId
import red.razvan.restcountries.data.models.DetailedCountry
import red.razvan.restcountries.data.models.Language
import red.razvan.restcountries.data.models.LanguageId

object SampleData {

  object CountryListItems {
    val Italy = CountryListItem(
      id = CountryId("ITA"),
      officialName = "Italian Republic",
      emojiFlag = "🇮🇹",
    )

    val Romania = CountryListItem(
      id = CountryId("ROU"),
      officialName = "Romania",
      emojiFlag = "🇷🇴",
    )

    val All = listOf(Italy, Romania)
  }

  object DetailedCountries {
    val UnknownCountryId = CountryId("UNKNOWN")

    val Italy = DetailedCountry(
      id = CountryListItems.Italy.id,
      officialName = CountryListItems.Italy.officialName,
      commonName = "Italy",
      emojiFlag = CountryListItems.Italy.emojiFlag,
      flag = DetailedCountry.Flag(
        png = "",
        svg = "",
        contentDescription = "Mamma mia",
      ),
      currencies = listOf(
        Currency(
          id = CurrencyId("EUR"),
          name = "Euro",
          symbol = "€",
        ),
      ),
      capital = listOf("Roma"),
      continents = listOf("Europe"),
      languages = listOf(
        Language(
          id = LanguageId("ita"),
          name = "Italian",
        ),
      ),
    )

    val Romania = DetailedCountry(
      id = CountryListItems.Romania.id,
      officialName = CountryListItems.Romania.officialName,
      commonName = "",
      emojiFlag = CountryListItems.Romania.emojiFlag,
      flag = DetailedCountry.Flag(
        png = "",
        svg = "",
        contentDescription = "Sarmale",
      ),
      currencies = listOf(
        Currency(
          id = CurrencyId("LEI"),
          name = "Romanian leu",
          symbol = "lei",
        ),
      ),
      capital = listOf("București"),
      continents = listOf("Europe"),
      languages = listOf(
        Language(
          id = LanguageId("rou"),
          name = "Romanian",
        ),
      ),
    )

    private val _all = listOf(Italy, Romania)

    val All = _all
      .associateBy { it.id }
  }
}
