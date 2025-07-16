// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.repository

import org.koin.dsl.module
import red.razvan.restcountries.data.db.DataDbModule
import red.razvan.restcountries.data.remote.DataRemoteModule
import red.razvan.restcountries.data.repository.internal.CountryIdToDbCountryIdMapper
import red.razvan.restcountries.data.repository.internal.CountryIdToRemoteMapper
import red.razvan.restcountries.data.repository.internal.DbCapitalToModelMapper
import red.razvan.restcountries.data.repository.internal.DbContinentToModelMapper
import red.razvan.restcountries.data.repository.internal.DbCountryHeaderToCountryListItemMapper
import red.razvan.restcountries.data.repository.internal.DbCountryIdToCountryIdMapper
import red.razvan.restcountries.data.repository.internal.DbCurrencyIdToCurrencyIdMapper
import red.razvan.restcountries.data.repository.internal.DbCurrencyToCurrencyMapper
import red.razvan.restcountries.data.repository.internal.DbLanguageIdToLanguageIdMapper
import red.razvan.restcountries.data.repository.internal.DbLanguageToLanguageMapper
import red.razvan.restcountries.data.repository.internal.DefaultCountryIdToDbCountryIdMapper
import red.razvan.restcountries.data.repository.internal.DefaultCountryIdToRemoteMapper
import red.razvan.restcountries.data.repository.internal.DefaultDbCapitalToModelMapper
import red.razvan.restcountries.data.repository.internal.DefaultDbContinentToModelMapper
import red.razvan.restcountries.data.repository.internal.DefaultDbCountryHeaderToCountryListItemMapper
import red.razvan.restcountries.data.repository.internal.DefaultDbCountryIdToCountryIdMapper
import red.razvan.restcountries.data.repository.internal.DefaultDbCurrencyIdToCurrencyIdMapper
import red.razvan.restcountries.data.repository.internal.DefaultDbCurrencyToCurrencyMapper
import red.razvan.restcountries.data.repository.internal.DefaultDbLanguageIdToLanguageIdMapper
import red.razvan.restcountries.data.repository.internal.DefaultDbLanguageToLanguageMapper
import red.razvan.restcountries.data.repository.internal.DefaultRemoteCountryHeaderToDbCountryHeaderMapper
import red.razvan.restcountries.data.repository.internal.DefaultRemoteCountryIdToDbCountryIdMapper
import red.razvan.restcountries.data.repository.internal.DefaultRemoteCurrencyIdToDbCurrencyIdMapper
import red.razvan.restcountries.data.repository.internal.DefaultRemoteDetailedCountryToDbCapitalMapper
import red.razvan.restcountries.data.repository.internal.DefaultRemoteDetailedCountryToDbContinentsMapper
import red.razvan.restcountries.data.repository.internal.DefaultRemoteDetailedCountryToDbCountryDetailsMapper
import red.razvan.restcountries.data.repository.internal.DefaultRemoteDetailedCountryToDbCountryHeaderMapper
import red.razvan.restcountries.data.repository.internal.DefaultRemoteDetailedCountryToDbCurrenciesMapper
import red.razvan.restcountries.data.repository.internal.DefaultRemoteDetailedCountryToDbLanguagesMapper
import red.razvan.restcountries.data.repository.internal.DefaultRemoteLanguageIdToDbLanguageIdMapper
import red.razvan.restcountries.data.repository.internal.RemoteCountryHeaderToDbCountryHeaderMapper
import red.razvan.restcountries.data.repository.internal.RemoteCountryIdToDbCountryIdMapper
import red.razvan.restcountries.data.repository.internal.RemoteCurrencyIdToDbCurrencyIdMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbCapitalMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbContinentsMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbCountryDetailsMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbCountryHeaderMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbCurrenciesMapper
import red.razvan.restcountries.data.repository.internal.RemoteDetailedCountryToDbLanguagesMapper
import red.razvan.restcountries.data.repository.internal.RemoteLanguageIdToDbLanguageIdMapper

val DataRepositoryModule = module {
  includes(DataDbModule, DataRemoteModule)

  single<DbCountryHeaderToCountryListItemMapper> {
    DefaultDbCountryHeaderToCountryListItemMapper(
      dbCountryIdToCountryIdMapper = get(),
    )
  }

  single<DbCountryIdToCountryIdMapper> {
    DefaultDbCountryIdToCountryIdMapper
  }

  single<DbLanguageIdToLanguageIdMapper> {
    DefaultDbLanguageIdToLanguageIdMapper
  }

  single<DbCurrencyIdToCurrencyIdMapper> {
    DefaultDbCurrencyIdToCurrencyIdMapper
  }

  single<DbCurrencyToCurrencyMapper> {
    DefaultDbCurrencyToCurrencyMapper(
      dbCurrencyIdToCurrencyIdMapper = get(),
    )
  }

  single<DbLanguageToLanguageMapper> {
    DefaultDbLanguageToLanguageMapper(
      dbLanguageIdToLanguageIdMapper = get(),
    )
  }

  single<DbCapitalToModelMapper> {
    DefaultDbCapitalToModelMapper
  }

  single<DbContinentToModelMapper> {
    DefaultDbContinentToModelMapper
  }

  single<CountryIdToDbCountryIdMapper> {
    DefaultCountryIdToDbCountryIdMapper
  }

  single<CountryIdToRemoteMapper> {
    DefaultCountryIdToRemoteMapper
  }

  single<RemoteCountryHeaderToDbCountryHeaderMapper> {
    DefaultRemoteCountryHeaderToDbCountryHeaderMapper(
      remoteCountryIdToDbCountryIdMapper = get(),
    )
  }

  single<RemoteCountryIdToDbCountryIdMapper> {
    DefaultRemoteCountryIdToDbCountryIdMapper
  }

  single<RemoteLanguageIdToDbLanguageIdMapper> {
    DefaultRemoteLanguageIdToDbLanguageIdMapper
  }

  single<RemoteDetailedCountryToDbCountryDetailsMapper> {
    DefaultRemoteDetailedCountryToDbCountryDetailsMapper(
      remoteCountryIdToDbCountryIdMapper = get(),
    )
  }

  single<RemoteDetailedCountryToDbCountryHeaderMapper> {
    DefaultRemoteDetailedCountryToDbCountryHeaderMapper(
      remoteCountryIdToDbCountryIdMapper = get(),
    )
  }

  single<RemoteDetailedCountryToDbLanguagesMapper> {
    DefaultRemoteDetailedCountryToDbLanguagesMapper(
      remoteLanguageIdToDbLanguageIdMapper = get(),
    )
  }

  single<RemoteCurrencyIdToDbCurrencyIdMapper> {
    DefaultRemoteCurrencyIdToDbCurrencyIdMapper
  }

  single<RemoteDetailedCountryToDbCapitalMapper> {
    DefaultRemoteDetailedCountryToDbCapitalMapper
  }

  single<RemoteDetailedCountryToDbContinentsMapper> {
    DefaultRemoteDetailedCountryToDbContinentsMapper
  }

  single<RemoteDetailedCountryToDbCurrenciesMapper> {
    DefaultRemoteDetailedCountryToDbCurrenciesMapper(
      remoteCurrencyIdToDbCurrencyIdMapper = get(),
    )
  }

  single<CountryRepository> {
    DefaultCountryRepository(
      client = get(),
      dispatchers = get(),
      runDatabaseTransaction = get(),
      countryHeaderDao = get(),
      countryDetailsDao = get(),
      currencyDao = get(),
      capitalDao = get(),
      continentDao = get(),
      languageDao = get(),
      countryHeaderCapitalCrossRefDao = get(),
      countryHeaderContinentCrossRefDao = get(),
      countryHeaderCurrencyCrossRefDao = get(),
      countryHeaderLanguageCrossRefDao = get(),
      dbLanguageToLanguageMapper = get(),
      dbCapitalToModelMapper = get(),
      dbContinentToModelMapper = get(),
      dbCurrencyToCurrencyMapper = get(),
      dbCountryHeaderToCountryListItemMapper = get(),
      dbCountryIdToCountryIdMapper = get(),
      countryIdToDbCountryIdMapper = get(),
      countryIdToRemoteMapper = get(),
      remoteDetailedCountryToDbCountryDetailsMapper = get(),
      remoteDetailedCountryToDbCountryHeaderMapper = get(),
      remoteCountryHeaderToDbCountryHeaderMapper = get(),
      remoteDetailedCountryToDbContinentsMapper = get(),
      remoteDetailedCountryToDbCurrenciesMapper = get(),
      remoteDetailedCountryToDbCapitalMapper = get(),
      remoteDetailedCountryToDbLanguagesMapper = get(),
    )
  }
}
