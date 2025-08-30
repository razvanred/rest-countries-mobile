// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import red.razvan.restcountries.testresources.android.koin.KoinTestRule

@RunWith(AndroidJUnit4::class)
class CountryHeaderDaoTests : KoinTest {

  @get:Rule
  val koinTestRule: KoinTestRule = KoinTestRule(modules = listOf(DataDbModule, InMemoryDatabaseModule))

  private val database: RoomAppDatabase by inject()

  private val countryHeaderDao: CountryHeaderDao by inject()
  private val countryDetailsDao: CountryDetailsDao by inject()
  private val languageDao: LanguageDao by inject()
  private val currencyDao: CurrencyDao by inject()
  private val capitalDao: CapitalDao by inject()
  private val continentDao: ContinentDao by inject()
  private val countryHeaderLanguageCrossRefDao: CountryHeaderLanguageCrossRefDao by inject()
  private val countryHeaderCurrencyCrossRefDao: CountryHeaderCurrencyCrossRefDao by inject()
  private val countryHeaderCapitalCrossRefDao: CountryHeaderCapitalCrossRefDao by inject()
  private val countryHeaderContinentCrossRefDao: CountryHeaderContinentCrossRefDao by inject()

  @Test
  fun insertAlreadyExistent_checkFailure() = runTest {
    val country = SampleData.CountryHeaders.Italy

    countryHeaderDao.insert(country)
    assertThat(countryHeaderDao.observeByIdOrNull(country.id).first())
      .isEqualTo(country)

    assertFailure {
      countryHeaderDao.insert(country)
    }
  }

  @Test
  fun deleteHeader_checkDetailsCascadeDeletion() = runTest {
    val headerToDelete = SampleData.CountryHeaders.Australia
    val headerToKeep = SampleData.CountryHeaders.Italy

    insertHeaderWithDetails(headerToDelete)
    insertHeaderWithDetails(headerToKeep)

    countryHeaderDao.deleteById(headerToDelete.id)

    assertThat(countryHeaderDao.observeByIdOrNull(id = headerToDelete.id).first())
      .isNull()
    assertThat(countryDetailsDao.observeByIdOrNull(id = headerToDelete.id).first())
      .isNull()
    assertThat(currencyDao.observeByCountryId(countryId = headerToDelete.id).first())
      .isEmpty()
    assertThat(continentDao.observeByCountryId(countryId = headerToDelete.id).first())
      .isEmpty()
    assertThat(languageDao.observeByCountryId(countryId = headerToDelete.id).first())
      .isEmpty()
    assertThat(capitalDao.observeByCountryId(countryId = headerToDelete.id).first())
      .isEmpty()

    // check if the deletion triggers worked
    assertThat(currencyDao.getAll())
      .containsOnly(*SampleData.Currencies.getByCountryId(headerToKeep.id).toTypedArray())

    assertThat(continentDao.getAll())
      .containsOnly(*SampleData.Continents.getByCountryId(headerToKeep.id).toTypedArray())

    assertThat(languageDao.getAll())
      .containsOnly(*SampleData.Languages.getByCountryId(headerToKeep.id).toTypedArray())

    assertThat(capitalDao.getAll())
      .containsOnly(*SampleData.Capital.getByCountryId(headerToKeep.id).toTypedArray())
  }

  private suspend fun insertHeaderWithDetails(header: CountryHeader) {
    countryHeaderDao.insert(header)
    countryDetailsDao.insert(SampleData.CountryDetails.getByCountryId(header.id))

    languageDao.upsert(SampleData.Languages.getByCountryId(header.id))
    countryHeaderLanguageCrossRefDao.insert(SampleData.CountryHeaderLanguageCrossRefs.getByCountryId(header.id))

    currencyDao.upsert(SampleData.Currencies.getByCountryId(header.id))
    countryHeaderCurrencyCrossRefDao.insert(SampleData.CountryHeaderCurrencyCrossRefs.getByCountryId(header.id))

    continentDao.upsert(SampleData.Continents.getByCountryId(header.id))
    countryHeaderContinentCrossRefDao.insert(SampleData.CountryHeaderContinentCrossRefs.getByCountryId(header.id))

    capitalDao.upsert(SampleData.Capital.getByCountryId(header.id))
    countryHeaderCapitalCrossRefDao.insert(SampleData.CountryHeaderCapitalCrossRefs.getByCountryId(header.id))
  }

  @After
  fun after() {
    database.close()
  }
}
