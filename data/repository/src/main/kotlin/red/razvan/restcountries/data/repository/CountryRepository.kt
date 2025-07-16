// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import red.razvan.restcountries.commons.kotlinx.coroutines.combine
import red.razvan.restcountries.core.kotlinx.coroutines.AppCoroutineDispatchers
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
import red.razvan.restcountries.data.models.CountryListItem
import red.razvan.restcountries.data.models.DetailedCountry
import red.razvan.restcountries.data.models.InvokeStatus
import red.razvan.restcountries.data.models.InvokeStatuses
import red.razvan.restcountries.data.models.NetworkFailure
import red.razvan.restcountries.data.models.NetworkFailures
import red.razvan.restcountries.data.remote.CountriesApi
import red.razvan.restcountries.data.repository.internal.CountryIdToDbCountryIdMapper
import red.razvan.restcountries.data.repository.internal.CountryIdToRemoteMapper
import red.razvan.restcountries.data.repository.internal.DbCapitalToModelMapper
import red.razvan.restcountries.data.repository.internal.DbContinentToModelMapper
import red.razvan.restcountries.data.repository.internal.DbCountryHeaderCapitalCrossRef
import red.razvan.restcountries.data.repository.internal.DbCountryHeaderContinentCrossRef
import red.razvan.restcountries.data.repository.internal.DbCountryHeaderCurrencyCrossRef
import red.razvan.restcountries.data.repository.internal.DbCountryHeaderLanguageCrossRef
import red.razvan.restcountries.data.repository.internal.DbCountryHeaderToCountryListItemMapper
import red.razvan.restcountries.data.repository.internal.DbCountryIdToCountryIdMapper
import red.razvan.restcountries.data.repository.internal.DbCurrencyToCurrencyMapper
import red.razvan.restcountries.data.repository.internal.DbLanguageToLanguageMapper
import red.razvan.restcountries.data.repository.internal.FailureRemoteResult
import red.razvan.restcountries.data.repository.internal.HttpStatusCodeFailureRemoteResult
import red.razvan.restcountries.data.repository.internal.RemoteCountryHeaderToDbCountryHeaderMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountry
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbCapitalMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbContinentsMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbCountryDetailsMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbCountryHeaderMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbCurrenciesMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbLanguagesMapper
import red.razvan.restcountries.data.repository.internal.RemoteResult
import red.razvan.restcountries.data.repository.internal.SuccessfulRemoteResult
import red.razvan.restcountries.data.repository.internal.UndefinedFailureRemoteResult

interface CountryRepository {

  fun observeListItems(): Flow<List<CountryListItem>>

  fun observeDetailsByIdOrNull(id: CountryId): Flow<DetailedCountry?>

  fun refreshListItems(): Flow<InvokeStatus<Unit, NetworkFailure>>

  fun refreshDetailsById(id: CountryId): Flow<InvokeStatus<Unit, NetworkFailure>>
}

internal class DefaultCountryRepository(
  private val client: CountriesApi,
  private val dispatchers: AppCoroutineDispatchers,
  private val runDatabaseTransaction: RunDatabaseTransaction,
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
  private val dbCountryHeaderToCountryListItemMapper: DbCountryHeaderToCountryListItemMapper,
  private val dbCurrencyToCurrencyMapper: DbCurrencyToCurrencyMapper,
  private val dbLanguageToLanguageMapper: DbLanguageToLanguageMapper,
  private val dbCapitalToModelMapper: DbCapitalToModelMapper,
  private val dbContinentToModelMapper: DbContinentToModelMapper,
  private val countryIdToDbCountryIdMapper: CountryIdToDbCountryIdMapper,
  private val dbCountryIdToCountryIdMapper: DbCountryIdToCountryIdMapper,
  private val countryIdToRemoteMapper: CountryIdToRemoteMapper,
  private val remoteCountryHeaderToDbCountryHeaderMapper: RemoteCountryHeaderToDbCountryHeaderMapper,
  private val remoteDetailedCountryToDbCountryHeaderMapper: RemoteDetailedCountryToDbCountryHeaderMapper,
  private val remoteDetailedCountryToDbLanguagesMapper: RemoteDetailedCountryToDbLanguagesMapper,
  private val remoteDetailedCountryToDbContinentsMapper: RemoteDetailedCountryToDbContinentsMapper,
  private val remoteDetailedCountryToDbCapitalMapper: RemoteDetailedCountryToDbCapitalMapper,
  private val remoteDetailedCountryToDbCurrenciesMapper: RemoteDetailedCountryToDbCurrenciesMapper,
  private val remoteDetailedCountryToDbCountryDetailsMapper: RemoteDetailedCountryToDbCountryDetailsMapper,
) : CountryRepository {

  override fun observeListItems(): Flow<List<CountryListItem>> = countryHeaderDao
    .observeAll()
    .map { entities ->
      entities.map { entity ->
        dbCountryHeaderToCountryListItemMapper.map(entity)
      }
    }

  override fun observeDetailsByIdOrNull(id: CountryId): Flow<DetailedCountry?> {
    val localId = countryIdToDbCountryIdMapper.map(id)

    return combine(
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
  }

  override fun refreshListItems(): Flow<InvokeStatus<Unit, NetworkFailure>> = observeRefreshCall(
    fetcher = {
      client.getCountryHeaders()
    },
    writer = { remotes ->
      val locals = remotes.map { remote ->
        remoteCountryHeaderToDbCountryHeaderMapper.map(remote)
      }

      runDatabaseTransaction {
        countryHeaderDao.deleteAll()
        countryHeaderDao.insert(locals)
      }
    },
  )

  override fun refreshDetailsById(id: CountryId): Flow<InvokeStatus<Unit, NetworkFailure>> = observeRefreshCall(
    fetcher = {
      client.getDetailedCountryByCca3(cca3 = countryIdToRemoteMapper.map(id))
    },
    writer = { remote ->
      persistRemoteDetailedCountry(remote)
    },
  )

  private suspend fun persistRemoteDetailedCountry(remote: RemoteDetailedCountry) {
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
          countryId = countryId,
          languageId = id,
        )
      }
    val countryHeaderCurrencyCrossRefs = currencies
      .map { (id) ->
        DbCountryHeaderCurrencyCrossRef(
          countryId = countryId,
          currencyId = id,
        )
      }
    val countryHeaderCapitalCrossRefs = capital
      .map { (name) ->
        DbCountryHeaderCapitalCrossRef(
          countryId = countryId,
          capitalName = name,
        )
      }
    val countryHeaderContinentCrossRefs = continents
      .map { (name) ->
        DbCountryHeaderContinentCrossRef(
          countryId = countryId,
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
  }

  private fun <T> observeRefreshCall(
    fetcher: suspend () -> RemoteResult<T>,
    writer: suspend (T) -> Unit,
  ): Flow<InvokeStatus<Unit, NetworkFailure>> = flow {
    emit(InvokeStatuses.InProgress)

    val status = when (val remoteResult = fetcher()) {
      is FailureRemoteResult -> {
        val failure = when (remoteResult) {
          is HttpStatusCodeFailureRemoteResult -> {
            NetworkFailures.HttpStatusCodeFailureResult(
              code = remoteResult.code,
              exception = remoteResult.exception,
            )
          }

          is UndefinedFailureRemoteResult -> {
            NetworkFailures.Undefined(exception = remoteResult.exception)
          }
        }
        InvokeStatuses.Failure(error = failure)
      }

      is SuccessfulRemoteResult<T> -> {
        withContext(dispatchers.databaseWrite) {
          writer(remoteResult.data)
        }
        InvokeStatuses.Successful(Unit)
      }
    }
    emit(status)
  }
}
