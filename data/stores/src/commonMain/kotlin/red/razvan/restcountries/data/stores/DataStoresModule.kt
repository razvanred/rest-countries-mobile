// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.stores

import org.koin.dsl.module
import red.razvan.restcountries.data.db.DataDbModule
import red.razvan.restcountries.data.remote.DataRemoteModule
import red.razvan.restcountries.data.stores.internal.CountryIdToDbCountryIdMapper
import red.razvan.restcountries.data.stores.internal.CountryIdToRemoteMapper
import red.razvan.restcountries.data.stores.internal.DbCapitalToModelMapper
import red.razvan.restcountries.data.stores.internal.DbContinentToModelMapper
import red.razvan.restcountries.data.stores.internal.DbCountryHeaderToCountryListItemMapper
import red.razvan.restcountries.data.stores.internal.DbCountryIdToCountryIdMapper
import red.razvan.restcountries.data.stores.internal.DbCurrencyIdToCurrencyIdMapper
import red.razvan.restcountries.data.stores.internal.DbCurrencyToCurrencyMapper
import red.razvan.restcountries.data.stores.internal.DbLanguageIdToLanguageIdMapper
import red.razvan.restcountries.data.stores.internal.DbLanguageToLanguageMapper
import red.razvan.restcountries.data.stores.internal.DefaultCountryIdToDbCountryIdMapper
import red.razvan.restcountries.data.stores.internal.DefaultCountryIdToRemoteMapper
import red.razvan.restcountries.data.stores.internal.DefaultDbCapitalToModelMapper
import red.razvan.restcountries.data.stores.internal.DefaultDbContinentToModelMapper
import red.razvan.restcountries.data.stores.internal.DefaultDbCountryHeaderToCountryListItemMapper
import red.razvan.restcountries.data.stores.internal.DefaultDbCountryIdToCountryIdMapper
import red.razvan.restcountries.data.stores.internal.DefaultDbCurrencyIdToCurrencyIdMapper
import red.razvan.restcountries.data.stores.internal.DefaultDbCurrencyToCurrencyMapper
import red.razvan.restcountries.data.stores.internal.DefaultDbLanguageIdToLanguageIdMapper
import red.razvan.restcountries.data.stores.internal.DefaultDbLanguageToLanguageMapper
import red.razvan.restcountries.data.stores.internal.DefaultRemoteCountryHeaderToDbCountryHeaderMapper
import red.razvan.restcountries.data.stores.internal.DefaultRemoteCountryIdToDbCountryIdMapper
import red.razvan.restcountries.data.stores.internal.DefaultRemoteCurrencyIdToDbCurrencyIdMapper
import red.razvan.restcountries.data.stores.internal.DefaultRemoteDetailedCountryToDbCapitalMapper
import red.razvan.restcountries.data.stores.internal.DefaultRemoteDetailedCountryToDbContinentsMapper
import red.razvan.restcountries.data.stores.internal.DefaultRemoteDetailedCountryToDbCountryDetailsMapper
import red.razvan.restcountries.data.stores.internal.DefaultRemoteDetailedCountryToDbCountryHeaderMapper
import red.razvan.restcountries.data.stores.internal.DefaultRemoteDetailedCountryToDbCurrenciesMapper
import red.razvan.restcountries.data.stores.internal.DefaultRemoteDetailedCountryToDbLanguagesMapper
import red.razvan.restcountries.data.stores.internal.DefaultRemoteLanguageIdToDbLanguageIdMapper
import red.razvan.restcountries.data.stores.internal.RemoteCountryHeaderToDbCountryHeaderMapper
import red.razvan.restcountries.data.stores.internal.RemoteCountryIdToDbCountryIdMapper
import red.razvan.restcountries.data.stores.internal.RemoteCurrencyIdToDbCurrencyIdMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbCapitalMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbContinentsMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbCountryDetailsMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbCountryHeaderMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbCurrenciesMapper
import red.razvan.restcountries.data.stores.internal.RemoteDetailedCountryToDbLanguagesMapper
import red.razvan.restcountries.data.stores.internal.RemoteLanguageIdToDbLanguageIdMapper

val DataStoresModule = module {
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

  single<CountryListItemsStore> {
    DefaultCountryListItemsStore(
      client = get(),
      remoteCountryHeaderToDbCountryHeaderMapper = get(),
      dbCountryHeaderToCountryListItemMapper = get(),
      runDatabaseTransaction = get(),
      countryHeaderDao = get(),
    )
  }

  single<DetailedCountryStore> {
    DefaultDetailedCountryStore(
      client = get(),
      runDatabaseTransaction = get(),
      countryIdToRemoteMapper = get(),
      countryHeaderDao = get(),
      countryDetailsDao = get(),
      currencyDao = get(),
      capitalDao = get(),
      languageDao = get(),
      continentDao = get(),
      countryHeaderContinentCrossRefDao = get(),
      countryHeaderCurrencyCrossRefDao = get(),
      countryHeaderLanguageCrossRefDao = get(),
      countryHeaderCapitalCrossRefDao = get(),
      dbCapitalToModelMapper = get(),
      dbContinentToModelMapper = get(),
      dbCurrencyToCurrencyMapper = get(),
      dbLanguageToLanguageMapper = get(),
      dbCountryIdToCountryIdMapper = get(),
      remoteDetailedCountryToDbCapitalMapper = get(),
      remoteDetailedCountryToDbLanguagesMapper = get(),
      remoteDetailedCountryToDbCurrenciesMapper = get(),
      remoteDetailedCountryToDbContinentsMapper = get(),
      remoteDetailedCountryToDbCountryHeaderMapper = get(),
      remoteDetailedCountryToDbCountryDetailsMapper = get(),
      countryIdToDbCountryIdMapper = get(),
    )
  }
}
