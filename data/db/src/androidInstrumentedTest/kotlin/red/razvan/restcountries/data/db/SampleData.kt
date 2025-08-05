// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

object SampleData {

  object CountryIds {
    val Italy = CountryId("ITA")
    val Romania = CountryId("ROU")
    val Australia = CountryId("AUS")
  }

  object CountryHeaders {
    val Italy = CountryHeader(
      id = CountryIds.Italy,
      commonName = "Italy",
      officialName = "Italian Republic",
      emojiFlag = "🇮🇹",
    )

    val Romania = CountryHeader(
      id = CountryIds.Romania,
      commonName = "",
      officialName = "Romania",
      emojiFlag = "🇷🇴",
    )

    val Australia = CountryHeader(
      id = CountryIds.Australia,
      commonName = "Australia",
      officialName = "Commonwealth of Australia",
      emojiFlag = "🇦🇺",
    )

    private val _all = listOf(Italy, Romania, Australia)

    val All = _all
      .associateBy { it.id }
  }

  object CountryDetails {
    val Italy = CountryDetails(
      id = CountryIds.Italy,
      svgFlag = "https://www.example.org/test.svg",
      pngFlag = "https://www.example.com/test.png",
      flagContentDescription = "alt",
      population = 3_400U,
    )

    val Romania = CountryDetails(
      id = CountryIds.Romania,
      svgFlag = "https://www.example.org/test.svg",
      pngFlag = "https://www.example.com/test.png",
      flagContentDescription = "alt",
      population = 3_400U,
    )

    val Australia = CountryDetails(
      id = CountryIds.Australia,
      svgFlag = "https://www.example.org/test.svg",
      pngFlag = "https://www.example.com/test.png",
      flagContentDescription = "alt",
      population = 25687041U,
    )

    private val _all = listOf(Italy, Romania, Australia)

    val All = _all
      .associateBy { it.id }

    fun getByCountryId(countryId: CountryId) = All.getValue(countryId)
  }

  object CountryHeaderCapitalCrossRefs {
    val Italy = getByCountryId(CountryIds.Italy)
    val Romania = getByCountryId(CountryIds.Romania)
    val Australia = getByCountryId(CountryIds.Australia)

    fun getByCountryId(countryId: CountryId) = Capital
      .getByCountryId(countryId)
      .map { capital ->
        CountryHeaderCapitalCrossRef(
          countryHeaderId = countryId,
          capitalName = capital.name,
        )
      }
  }

  object Capital {
    val Rome = Capital("Rome")
    val Bucharest = Capital("București")
    val Canberra = Capital("Canberra")

    fun getByCountryId(countryId: CountryId) = when (countryId) {
      CountryIds.Italy -> listOf(Rome)
      CountryIds.Romania -> listOf(Bucharest)
      CountryIds.Australia -> listOf(Canberra)
      else -> error("Capital not available for countryId = $countryId")
    }
  }

  object CountryHeaderCurrencyCrossRefs {
    val Italy = getByCountryId(CountryIds.Italy)
    val Romania = getByCountryId(CountryIds.Romania)
    val Australia = getByCountryId(CountryIds.Australia)

    fun getByCountryId(countryId: CountryId) = Currencies
      .getByCountryId(countryId)
      .map { currency ->
        CountryHeaderCurrencyCrossRef(
          countryHeaderId = countryId,
          currencyId = currency.id,
        )
      }
  }

  object Currencies {
    val Euro = Currency(id = CurrencyId("EUR"), name = "Euro", symbol = "€")
    val Leu = Currency(id = CurrencyId("LEU"), name = "Leu", symbol = "lei")
    val AustralianDollar =
      Currency(id = CurrencyId("AUD"), name = "Australian dollar", symbol = "$")

    fun getByCountryId(countryId: CountryId) = when (countryId) {
      CountryHeaders.Italy.id -> listOf(Euro)
      CountryHeaders.Romania.id -> listOf(Leu)
      CountryHeaders.Australia.id -> listOf(AustralianDollar)
      else -> error("Currency not found for countryId = $countryId")
    }
  }

  object CountryHeaderLanguageCrossRefs {
    val Italy = getByCountryId(CountryHeaders.Italy.id)
    val Romania = getByCountryId(CountryHeaders.Romania.id)
    val Australia = getByCountryId(CountryHeaders.Australia.id)

    fun getByCountryId(countryId: CountryId) = Languages.getByCountryId(countryId)
      .map { language ->
        CountryHeaderLanguageCrossRef(
          countryHeaderId = countryId,
          languageId = language.id,
        )
      }
  }

  object Languages {
    val Italian = Language(id = LanguageId("IT"), name = "Italiano")
    val Romanian = Language(id = LanguageId("RO"), name = "Română")
    val English = Language(id = LanguageId("eng"), name = "English")

    fun getByCountryId(countryId: CountryId): List<Language> = when (countryId) {
      CountryHeaders.Italy.id -> listOf(Italian)
      CountryHeaders.Romania.id -> listOf(Romanian)
      CountryHeaders.Australia.id -> listOf(English)
      else -> error("Languages not defined for countryId = $countryId")
    }
  }

  object CountryHeaderContinentCrossRefs {
    val Italy = getByCountryId(CountryIds.Italy)
    val Romania = getByCountryId(CountryIds.Romania)
    val Australia = getByCountryId(CountryIds.Australia)

    fun getByCountryId(countryId: CountryId) = Continents
      .getByCountryId(countryId)
      .map { continent ->
        CountryHeaderContinentCrossRef(
          countryHeaderId = countryId,
          continentName = continent.name,
        )
      }
  }

  object Continents {
    val Europe = Continent("Europe")
    val Oceania = Continent("Australia")

    fun getByCountryId(countryId: CountryId) = when (countryId) {
      CountryHeaders.Italy.id, CountryHeaders.Romania.id -> listOf(Europe)
      CountryHeaders.Australia.id -> listOf(Oceania)
      else -> error("Continents not defined for countryId = $countryId")
    }
  }
}
