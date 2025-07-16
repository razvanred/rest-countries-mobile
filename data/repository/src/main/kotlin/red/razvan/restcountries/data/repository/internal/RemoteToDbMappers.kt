// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.repository.internal

import red.razvan.restcountries.core.utils.Mapper

internal interface RemoteCountryHeaderToDbCountryHeaderMapper : Mapper<RemoteCountryHeader, DbCountryHeader>

internal interface RemoteCountryIdToDbCountryIdMapper : Mapper<String, DbCountryId>
internal interface RemoteLanguageIdToDbLanguageIdMapper : Mapper<String, DbLanguageId>
internal interface RemoteDetailedCountryToDbCountryDetailsMapper : Mapper<RemoteDetailedCountry, DbCountryDetails>

internal interface RemoteDetailedCountryToDbCountryHeaderMapper : Mapper<RemoteDetailedCountry, DbCountryHeader>

internal interface RemoteDetailedCountryToDbLanguagesMapper : Mapper<RemoteDetailedCountry, List<DbLanguage>>

internal interface RemoteCurrencyIdToDbCurrencyIdMapper : Mapper<String, DbCurrencyId>
internal interface RemoteDetailedCountryToDbCapitalMapper : Mapper<RemoteDetailedCountry, List<DbCapital>>

internal interface RemoteDetailedCountryToDbContinentsMapper : Mapper<RemoteDetailedCountry, List<DbContinent>>

internal interface RemoteDetailedCountryToDbCurrenciesMapper : Mapper<RemoteDetailedCountry, List<DbCurrency>>

internal class DefaultRemoteCountryHeaderToDbCountryHeaderMapper(
  private val remoteCountryIdToDbCountryIdMapper: RemoteCountryIdToDbCountryIdMapper,
) : RemoteCountryHeaderToDbCountryHeaderMapper {

  override fun map(source: RemoteCountryHeader): DbCountryHeader = with(source) {
    DbCountryHeader(
      id = remoteCountryIdToDbCountryIdMapper.map(cca3),
      commonName = name.common,
      officialName = name.official,
      emojiFlag = emojiFlag,
    )
  }
}

internal object DefaultRemoteCountryIdToDbCountryIdMapper : RemoteCountryIdToDbCountryIdMapper {

  override fun map(source: String): DbCountryId = DbCountryId(source)
}

internal object DefaultRemoteLanguageIdToDbLanguageIdMapper : RemoteLanguageIdToDbLanguageIdMapper {

  override fun map(source: String): DbLanguageId = DbLanguageId(source)
}

internal class DefaultRemoteDetailedCountryToDbCountryDetailsMapper(
  private val remoteCountryIdToDbCountryIdMapper: RemoteCountryIdToDbCountryIdMapper,
) : RemoteDetailedCountryToDbCountryDetailsMapper {

  override fun map(source: RemoteDetailedCountry): DbCountryDetails = with(source) {
    DbCountryDetails(
      id = remoteCountryIdToDbCountryIdMapper.map(cca3),
      svgFlag = flag.svg,
      pngFlag = flag.png,
      flagContentDescription = flag.alt,
      population = population,
    )
  }
}

internal class DefaultRemoteDetailedCountryToDbCountryHeaderMapper(
  private val remoteCountryIdToDbCountryIdMapper: RemoteCountryIdToDbCountryIdMapper,
) : RemoteDetailedCountryToDbCountryHeaderMapper {

  override fun map(source: RemoteDetailedCountry): DbCountryHeader = with(source) {
    DbCountryHeader(
      id = remoteCountryIdToDbCountryIdMapper.map(cca3),
      commonName = name.common,
      officialName = name.official,
      emojiFlag = emojiFlag,
    )
  }
}

internal class DefaultRemoteDetailedCountryToDbLanguagesMapper(
  private val remoteLanguageIdToDbLanguageIdMapper: RemoteLanguageIdToDbLanguageIdMapper,
) : RemoteDetailedCountryToDbLanguagesMapper {

  override fun map(source: RemoteDetailedCountry): List<DbLanguage> = source
    .languages
    .map { (id, name) ->
      DbLanguage(
        id = remoteLanguageIdToDbLanguageIdMapper.map(id),
        name = name,
      )
    }
}

internal object DefaultRemoteCurrencyIdToDbCurrencyIdMapper : RemoteCurrencyIdToDbCurrencyIdMapper {

  override fun map(source: String): DbCurrencyId = DbCurrencyId(source)
}

internal object DefaultRemoteDetailedCountryToDbCapitalMapper :
  RemoteDetailedCountryToDbCapitalMapper {

  override fun map(source: RemoteDetailedCountry): List<DbCapital> = source
    .capital
    .map { name -> DbCapital(name = name) }
}

internal object DefaultRemoteDetailedCountryToDbContinentsMapper :
  RemoteDetailedCountryToDbContinentsMapper {

  override fun map(source: RemoteDetailedCountry): List<DbContinent> = source
    .continents
    .map { name -> DbContinent(name = name) }
}

internal class DefaultRemoteDetailedCountryToDbCurrenciesMapper(
  private val remoteCurrencyIdToDbCurrencyIdMapper: RemoteCurrencyIdToDbCurrencyIdMapper,
) : RemoteDetailedCountryToDbCurrenciesMapper {

  override fun map(source: RemoteDetailedCountry): List<DbCurrency> = source
    .currencies
    .map { (id, currency) ->
      DbCurrency(
        id = remoteCurrencyIdToDbCurrencyIdMapper.map(id),
        name = currency.name,
        symbol = currency.symbol,
      )
    }
}
