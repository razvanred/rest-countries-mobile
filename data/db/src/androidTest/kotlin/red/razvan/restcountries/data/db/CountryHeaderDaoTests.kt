// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import red.razvan.restcountries.testresources.android.koin.KoinTestRule

@RunWith(AndroidJUnit4::class)
class CountryHeaderDaoTests : KoinTest {

  private val instrumentationTestModule = module {
    single {
      Room.inMemoryDatabaseBuilder(androidContext(), RoomAppDatabase::class.java)
        .build()
    }
  }

  @get:Rule
  val koinTestRule: KoinTestRule = KoinTestRule(modules = listOf(DataDbModule, instrumentationTestModule))

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
    val header = SampleData.CountryHeaders.Romania
    insertHeaderWithDetails(header)
    insertHeaderWithDetails(SampleData.CountryHeaders.Italy)

    countryHeaderDao.deleteById(header.id)

    assertThat(countryHeaderDao.observeByIdOrNull(id = header.id).first())
      .isNull()
    assertThat(countryDetailsDao.observeByIdOrNull(id = header.id).first())
      .isNull()
    assertThat(currencyDao.observeByCountryId(countryId = header.id).first())
      .isEmpty()
    assertThat(continentDao.observeByCountryId(countryId = header.id).first())
      .isEmpty()
    assertThat(languageDao.observeByCountryId(countryId = header.id).first())
      .isEmpty()
    assertThat(capitalDao.observeByCountryId(countryId = header.id).first())
      .isEmpty()
  }

  private suspend fun insertHeaderWithDetails(header: CountryHeader) {
    countryHeaderDao.insert(header)
    countryDetailsDao.insert(SampleData.CountryDetails.All.getValue(header.id))

    languageDao.upsert(SampleData.Languages.All.getValue(header.id))
    countryHeaderLanguageCrossRefDao.insert(SampleData.CountryHeaderLanguageCrossRefs.All.getValue(header.id))

    currencyDao.upsert(SampleData.Currencies.All.getValue(header.id))
    countryHeaderCurrencyCrossRefDao.insert(SampleData.CountryHeaderCurrencyCrossRefs.All.getValue(header.id))

    continentDao.upsert(SampleData.Continents.All.getValue(header.id))
    countryHeaderContinentCrossRefDao.insert(SampleData.CountryHeaderContinentCrossRefs.All.getValue(header.id))

    capitalDao.upsert(SampleData.Capital.All.getValue(header.id))
    countryHeaderCapitalCrossRefDao.insert(SampleData.CountryHeaderCapitalCrossRefs.All.getValue(header.id))
  }

  @After
  fun after() {
    database.close()
  }
}
