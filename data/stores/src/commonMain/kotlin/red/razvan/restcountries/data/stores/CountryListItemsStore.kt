// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.stores

import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import red.razvan.restcountries.data.db.CountryHeaderDao
import red.razvan.restcountries.data.db.RunDatabaseTransaction
import red.razvan.restcountries.data.models.CountryListItem
import red.razvan.restcountries.data.remote.CountriesApi
import red.razvan.restcountries.data.stores.internal.DbCountryHeaderToCountryListItemMapper
import red.razvan.restcountries.data.stores.internal.RemoteCountryHeader
import red.razvan.restcountries.data.stores.internal.RemoteCountryHeaderToDbCountryHeaderMapper
import red.razvan.restcountries.data.stores.internal.ofRemoteResult

interface CountryListItemsStore : Store<Unit, List<CountryListItem>>

internal class DefaultCountryListItemsStore(
  private val client: CountriesApi,
  private val remoteCountryHeaderToDbCountryHeaderMapper: RemoteCountryHeaderToDbCountryHeaderMapper,
  private val dbCountryHeaderToCountryListItemMapper: DbCountryHeaderToCountryListItemMapper,
  private val runDatabaseTransaction: RunDatabaseTransaction,
  private val countryHeaderDao: CountryHeaderDao,
) : CountryListItemsStore,
  Store<Unit, List<CountryListItem>> by StoreBuilder
    .from(
      fetcher = Fetcher.ofRemoteResult {
        client.getCountryHeaders()
      },
      sourceOfTruth = SourceOfTruth.of(
        reader = { _: Unit ->
          countryHeaderDao
            .observeAll()
            .map { entities ->
              entities.map { entity ->
                dbCountryHeaderToCountryListItemMapper.map(entity)
              }
            }
        },
        writer = { _, remotes: List<RemoteCountryHeader> ->
          val locals = remotes.map { remote ->
            remoteCountryHeaderToDbCountryHeaderMapper.map(remote)
          }

          runDatabaseTransaction {
            countryHeaderDao.deleteAll()
            countryHeaderDao.insert(locals)
          }
        },
      ),
    )
    .build()
