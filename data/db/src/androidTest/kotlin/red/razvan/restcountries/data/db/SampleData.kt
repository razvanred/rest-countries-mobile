// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

object SampleData {

  object CountryHeaders {
    val Italy = CountryHeader(
      id = CountryId("ITA"),
      commonName = "Italy",
      officialName = "Italian Republic",
      emojiFlag = "🇮🇹",
    )

    val Romania = CountryHeader(
      id = CountryId("ROU"),
      commonName = "",
      officialName = "Romania",
      emojiFlag = "🇷🇴",
    )

    private val _all = listOf(Italy, Romania)

    val All = _all
      .associateBy { it.id }
  }

  object CountryDetails {
    val Italy = CountryDetails(
      id = CountryHeaders.Italy.id,
      svgFlag = "https://www.example.org/test.svg",
      pngFlag = "https://www.example.com/test.png",
      flagContentDescription = "alt",
      population = 3_400U,
    )

    val Romania = CountryDetails(
      id = CountryHeaders.Romania.id,
      svgFlag = "https://www.example.org/test.svg",
      pngFlag = "https://www.example.com/test.png",
      flagContentDescription = "alt",
      population = 3_400U,
    )

    private val _all = listOf(Italy, Romania)

    val All = _all
      .associateBy { it.id }

    fun getByCountryId(countryId: CountryId) = All.getValue(countryId)
  }

  object CountryHeaderCapitalCrossRefs {
    val Italy = Capital.Italy.map { capital ->
      CountryHeaderCapitalCrossRef(CountryHeaders.Italy.id, capital.name)
    }
    val Romania = Capital.Romania.map { capital ->
      CountryHeaderCapitalCrossRef(CountryHeaders.Romania.id, capital.name)
    }

    val All = listOf(Italy, Romania)
      .associateBy { (ref) -> ref.countryHeaderId }

    fun getByCountryId(countryId: CountryId) = All.getValue(countryId)
  }

  object Capital {
    val Italy = listOf(Capital("Rome"))
    val Romania = listOf(Capital("București"))

    val All = mapOf(
      CountryHeaders.Italy.id to Italy,
      CountryHeaders.Romania.id to Romania,
    )

    fun getByCountryId(countryId: CountryId) = All.getValue(countryId)
  }

  object CountryHeaderCurrencyCrossRefs {
    val Italy = Currencies.Italy.map { currency ->
      CountryHeaderCurrencyCrossRef(
        countryHeaderId = CountryHeaders.Italy.id,
        currencyId = currency.id,
      )
    }

    val Romania = Currencies.Romania.map { currency ->
      CountryHeaderCurrencyCrossRef(
        countryHeaderId = CountryHeaders.Romania.id,
        currencyId = currency.id,
      )
    }

    val All = listOf(Italy, Romania)
      .associateBy { (ref) -> ref.countryHeaderId }

    fun getByCountryId(countryId: CountryId) = All.getValue(countryId)
  }

  object Currencies {
    val Italy = listOf(Currency(id = CurrencyId("EUR"), name = "Euro", symbol = "€"))
    val Romania = listOf(Currency(id = CurrencyId("LEU"), name = "Leu", symbol = "lei"))

    val All = mapOf(
      CountryHeaders.Italy.id to Italy,
      CountryHeaders.Romania.id to Romania,
    )

    fun getByCountryId(countryId: CountryId) = All.getValue(countryId)
  }

  object CountryHeaderLanguageCrossRefs {
    val Italy = Languages.Italy.map { language ->
      CountryHeaderLanguageCrossRef(
        countryHeaderId = CountryHeaders.Italy.id,
        languageId = language.id,
      )
    }

    val Romania = Languages.Romania.map { language ->
      CountryHeaderLanguageCrossRef(
        countryHeaderId = CountryHeaders.Romania.id,
        languageId = language.id,
      )
    }

    fun getByCountryId(countryId: CountryId) = All.getValue(countryId)

    val All = listOf(Italy, Romania)
      .associateBy { (ref) -> ref.countryHeaderId }
  }

  object Languages {
    val Italy = listOf(Language(id = LanguageId("IT"), name = "Italiano"))
    val Romania = listOf(Language(id = LanguageId("RO"), name = "Română"))

    val All = mapOf(
      CountryHeaders.Italy.id to Italy,
      CountryHeaders.Romania.id to Romania,
    )

    fun getByCountryId(countryId: CountryId) = All.getValue(countryId)
  }

  object CountryHeaderContinentCrossRefs {
    val Italy = Continents.Italy.map { continent ->
      CountryHeaderContinentCrossRef(
        countryHeaderId = CountryHeaders.Italy.id,
        continentName = continent.name,
      )
    }

    val Romania = Continents.Romania.map { continent ->
      CountryHeaderContinentCrossRef(
        countryHeaderId = CountryHeaders.Romania.id,
        continentName = continent.name,
      )
    }

    val All = listOf(Italy, Romania)
      .associateBy { (ref) -> ref.countryHeaderId }

    fun getByCountryId(countryId: CountryId) = All.getValue(countryId)
  }

  object Continents {
    val Italy = listOf(Continent("Europe"))
    val Romania = listOf(Continent("Europe"))

    val All = mapOf(
      CountryHeaders.Italy.id to Italy,
      CountryHeaders.Romania.id to Romania,
    )

    fun getByCountryId(countryId: CountryId) = All.getValue(countryId)
  }
}
