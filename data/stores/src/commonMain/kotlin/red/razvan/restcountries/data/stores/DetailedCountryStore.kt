// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.stores

import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import red.razvan.restcountries.commons.kotlinx.coroutines.combine
import red.razvan.restcountries.data.db.CapitalDao
import red.razvan.restcountries.data.db.ContinentDao
import red.razvan.restcountries.data.db.CountryDetailsDao
import red.razvan.restcountries.data.db.CountryHeaderCapitalCrossRefDao
import red.razvan.restcountries.data.db.CountryHeaderContinentCrossRefDao
import red.razvan.restcountries.data.db.CountryHeaderCurrencyCrossRefDao
import red.razvan.restcountries.data.db.CountryHeaderDao
import red.razvan.restcountries.data.db.CountryHeaderLanguageCrossRefDao
import red.razvan.restcountries.data.db.CurrencyDao
import red.razvan.restcountries.data.db.LanguageDao
import red.razvan.restcountries.data.db.RunDatabaseTransaction
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.DetailedCountry
import red.razvan.restcountries.data.remote.CountriesApi
import red.razvan.restcountries.data.stores.internal.CountryIdToDbCountryIdMapper
import red.razvan.restcountries.data.stores.internal.CountryIdToRemoteMapper
import red.razvan.restcountries.data.stores.internal.DbCapitalToModelMapper
import red.razvan.restcountries.data.stores.internal.DbContinentToModelMapper
import red.razvan.restcountries.data.stores.internal.DbCountryHeaderCapitalCrossRef
import red.razvan.restcountries.data.stores.internal.DbCountryHeaderContinentCrossRef
import red.razvan.restcountries.data.stores.internal.DbCountryHeaderCurrencyCrossRef
import red.razvan.restcountries.data.stores.internal.DbCountryHeaderLanguageCrossRef
import red.razvan.restcountries.data.stores.internal.DbCountryIdToCountryIdMapper
import red.razvan.restcountries.data.stores.internal.DbCurrencyToCurrencyMapper
import red.razvan.restcountries.data.stores.internal.DbLanguageToLanguageMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbCapitalMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbContinentsMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbCountryDetailsMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbCountryHeaderMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbCurrenciesMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbLanguagesMapper
import red.razvan.restcountries.data.stores.internal.ofRemoteResult

interface DetailedCountryStore : Store<CountryId, DetailedCountry>

internal class DefaultDetailedCountryStore(
  private val client: CountriesApi,
  private val runDatabaseTransaction: RunDatabaseTransaction,
  private val countryIdToRemoteMapper: CountryIdToRemoteMapper,
  private val countryHeaderDao: CountryHeaderDao,
  private val countryDetailsDao: CountryDetailsDao,
  private val currencyDao: CurrencyDao,
  private val capitalDao: CapitalDao,
  private val continentDao: ContinentDao,
  private val languageDao: LanguageDao,
  private val countryHeaderCapitalCrossRefDao: CountryHeaderCapitalCrossRefDao,
  private val countryHeaderContinentCrossRefDao: CountryHeaderContinentCrossRefDao,
  private val countryHeaderCurrencyCrossRefDao: CountryHeaderCurrencyCrossRefDao,
  private val countryHeaderLanguageCrossRefDao: CountryHeaderLanguageCrossRefDao,
  private val countryIdToDbCountryIdMapper: CountryIdToDbCountryIdMapper,
  private val dbCapitalToModelMapper: DbCapitalToModelMapper,
  private val dbContinentToModelMapper: DbContinentToModelMapper,
  private val dbCurrencyToCurrencyMapper: DbCurrencyToCurrencyMapper,
  private val dbLanguageToLanguageMapper: DbLanguageToLanguageMapper,
  private val dbCountryIdToCountryIdMapper: DbCountryIdToCountryIdMapper,
  private val remoteDetailedCountryToDbCountryHeaderMapper: RemoteDetailedCountryToDbCountryHeaderMapper,
  private val remoteDetailedCountryToDbLanguagesMapper: RemoteDetailedCountryToDbLanguagesMapper,
  private val remoteDetailedCountryToDbContinentsMapper: RemoteDetailedCountryToDbContinentsMapper,
  private val remoteDetailedCountryToDbCapitalMapper: RemoteDetailedCountryToDbCapitalMapper,
  private val remoteDetailedCountryToDbCurrenciesMapper: RemoteDetailedCountryToDbCurrenciesMapper,
  private val remoteDetailedCountryToDbCountryDetailsMapper: RemoteDetailedCountryToDbCountryDetailsMapper,
) : DetailedCountryStore,
  Store<CountryId, DetailedCountry> by StoreBuilder
    .from(
      fetcher = Fetcher.ofRemoteResult { id ->
        client.getDetailedCountryByCca3(countryIdToRemoteMapper.map(id))
      },
      sourceOfTruth = SourceOfTruth
        .of(
          reader = { id: CountryId ->
            val localId = countryIdToDbCountryIdMapper.map(id)

            combine(
              countryHeaderDao.observeByIdOrNull(localId),
              countryDetailsDao.observeByIdOrNull(localId),
              capitalDao.observeByCountryId(localId),
              continentDao.observeByCountryId(localId),
              currencyDao.observeByCountryId(localId),
              languageDao.observeByCountryId(localId),
            ) { header, details, capital, continents, currencies, languages ->
              if (header == null) return@combine null
              if (details == null) return@combine null

              DetailedCountry(
                id = dbCountryIdToCountryIdMapper.map(header.id),
                officialName = header.officialName,
                commonName = header.commonName,
                flag = DetailedCountry.Flag(
                  png = details.pngFlag,
                  svg = details.svgFlag,
                  contentDescription = details.flagContentDescription,
                ),
                currencies = currencies.map { currency ->
                  dbCurrencyToCurrencyMapper.map(currency)
                },
                languages = languages.map { language ->
                  dbLanguageToLanguageMapper.map(language)
                },
                capital = capital.map {
                  dbCapitalToModelMapper.map(it)
                },
                continents = continents.map { continent ->
                  dbContinentToModelMapper.map(continent)
                },
                emojiFlag = header.emojiFlag,
              )
            }
          },
          writer = { _, remote ->
            val header = remoteDetailedCountryToDbCountryHeaderMapper.map(remote)
            val countryId = header.id
            val details = remoteDetailedCountryToDbCountryDetailsMapper.map(remote)
            val languages = remoteDetailedCountryToDbLanguagesMapper.map(remote)
            val currencies = remoteDetailedCountryToDbCurrenciesMapper.map(remote)
            val capital = remoteDetailedCountryToDbCapitalMapper.map(remote)
            val continents = remoteDetailedCountryToDbContinentsMapper.map(remote)

            val countryHeaderLanguageCrossRefs = languages
              .map { (id) ->
                DbCountryHeaderLanguageCrossRef(
                  countryHeaderId = countryId,
                  languageId = id,
                )
              }
            val countryHeaderCurrencyCrossRefs = currencies
              .map { (id) ->
                DbCountryHeaderCurrencyCrossRef(
                  countryHeaderId = countryId,
                  currencyId = id,
                )
              }
            val countryHeaderCapitalCrossRefs = capital
              .map { (name) ->
                DbCountryHeaderCapitalCrossRef(
                  countryHeaderId = countryId,
                  capitalName = name,
                )
              }
            val countryHeaderContinentCrossRefs = continents
              .map { (name) ->
                DbCountryHeaderContinentCrossRef(
                  countryHeaderId = countryId,
                  continentName = name,
                )
              }

            runDatabaseTransaction {
              // Clears in cascade the data from all tables for that particular country
              countryHeaderDao.deleteById(countryId)

              countryHeaderDao.insert(header)
              countryDetailsDao.insert(details)

              languageDao.upsert(languages)
              countryHeaderLanguageCrossRefDao.insert(countryHeaderLanguageCrossRefs)

              currencyDao.upsert(currencies)
              countryHeaderCurrencyCrossRefDao.insert(countryHeaderCurrencyCrossRefs)

              capitalDao.upsert(capital)
              countryHeaderCapitalCrossRefDao.insert(countryHeaderCapitalCrossRefs)

              continentDao.upsert(continents)
              countryHeaderContinentCrossRefDao.insert(countryHeaderContinentCrossRefs)
            }
          },
        ),
    )
    .build()
