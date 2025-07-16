// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.repository.internal

import red.razvan.restcountries.core.utils.Mapper
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.CountryListItem
import red.razvan.restcountries.data.models.Currency
import red.razvan.restcountries.data.models.CurrencyId
import red.razvan.restcountries.data.models.Language
import red.razvan.restcountries.data.models.LanguageId

internal interface DbCountryHeaderToCountryListItemMapper : Mapper<DbCountryHeader, CountryListItem>
internal interface DbCountryIdToCountryIdMapper : Mapper<DbCountryId, CountryId>
internal interface DbLanguageIdToLanguageIdMapper : Mapper<DbLanguageId, LanguageId>
internal interface DbCurrencyIdToCurrencyIdMapper : Mapper<DbCurrencyId, CurrencyId>
internal interface DbCapitalToModelMapper : Mapper<DbCapital, String>
internal interface DbContinentToModelMapper : Mapper<DbContinent, String>
internal interface DbCurrencyToCurrencyMapper : Mapper<DbCurrency, Currency>
internal interface DbLanguageToLanguageMapper : Mapper<DbLanguage, Language>

internal class DefaultDbCountryHeaderToCountryListItemMapper(
  private val dbCountryIdToCountryIdMapper: DbCountryIdToCountryIdMapper,
) : DbCountryHeaderToCountryListItemMapper {

  override fun map(source: DbCountryHeader): CountryListItem = with(source) {
    CountryListItem(
      id = dbCountryIdToCountryIdMapper.map(id),
      officialName = officialName,
      emojiFlag = emojiFlag,
    )
  }
}

internal object DefaultDbCountryIdToCountryIdMapper : DbCountryIdToCountryIdMapper {

  override fun map(source: DbCountryId): CountryId = with(source) {
    CountryId(value = value)
  }
}

internal object DefaultDbLanguageIdToLanguageIdMapper : DbLanguageIdToLanguageIdMapper {

  override fun map(source: DbLanguageId): LanguageId = with(source) {
    LanguageId(value = value)
  }
}

internal object DefaultDbCurrencyIdToCurrencyIdMapper : DbCurrencyIdToCurrencyIdMapper {

  override fun map(source: DbCurrencyId): CurrencyId = with(source) {
    CurrencyId(value = value)
  }
}

internal class DefaultDbLanguageToLanguageMapper(
  private val dbLanguageIdToLanguageIdMapper: DbLanguageIdToLanguageIdMapper,
) : DbLanguageToLanguageMapper {

  override fun map(source: DbLanguage): Language = with(source) {
    Language(
      id = dbLanguageIdToLanguageIdMapper.map(id),
      name = name,
    )
  }
}

internal class DefaultDbCurrencyToCurrencyMapper(
  private val dbCurrencyIdToCurrencyIdMapper: DbCurrencyIdToCurrencyIdMapper,
) : DbCurrencyToCurrencyMapper {

  override fun map(source: DbCurrency): Currency = with(source) {
    Currency(
      id = dbCurrencyIdToCurrencyIdMapper.map(id),
      name = name,
      symbol = symbol,
    )
  }
}

internal object DefaultDbCapitalToModelMapper : DbCapitalToModelMapper {

  override fun map(source: DbCapital): String = source.name
}

internal object DefaultDbContinentToModelMapper : DbContinentToModelMapper {

  override fun map(source: DbContinent): String = source.name
}
