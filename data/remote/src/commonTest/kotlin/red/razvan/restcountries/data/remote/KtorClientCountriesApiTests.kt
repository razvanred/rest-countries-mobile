// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.logging.Logger
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declare
import red.razvan.restcountries.intellij.annotations.FormatLanguage
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class KtorClientCountriesApiTests : KoinTest {

  private val unitTestModule = module {
    single<Logger> {
      object : Logger {
        override fun log(message: String) {
          println(message)
        }
      }
    }
  }

  @BeforeTest
  fun beforeTest() {
    startKoin {
      modules(DataRemoteModule, unitTestModule)
    }
  }

  private val client: CountriesApi by inject()

  @Test
  fun `By giving a successful headers JSON response check correct parse and result`() = runTest {
    @FormatLanguage("json")
    val body = """
        [
          {
            "name": {
              "common": "Togo",
              "official": "Togolese Republic",
              "nativeName": {
                "fra": {
                  "official": "République togolaise",
                  "common": "Togo"
                }
              }
            },
            "cca3": "TGO",
            "flag": "🇹🇬"
          },
          {
            "name": {
              "common": "Mayotte",
              "official": "Department of Mayotte",
              "nativeName": {
                "fra": {
                  "official": "Département de Mayotte",
                  "common": "Mayotte"
                }
              }
            },
            "cca3": "MYT",
            "flag": "🇾🇹"
          },
          {
            "name": {
              "common": "Georgia",
              "official": "Georgia",
              "nativeName": {
                "kat": {
                  "official": "საქართველო",
                  "common": "საქართველო"
                }
              }
            },
            "cca3": "GEO",
            "flag": "🇬🇪"
          }
        ]
    """.trimIndent()

    val expectedResult = listOf(
      CountryHeader(
        cca3 = "TGO",
        name = CountryName(
          common = "Togo",
          official = "Togolese Republic",
        ),
        emojiFlag = "🇹🇬",
      ),
      CountryHeader(
        cca3 = "MYT",
        name = CountryName(
          common = "Mayotte",
          official = "Department of Mayotte",
        ),
        emojiFlag = "🇾🇹",
      ),
      CountryHeader(
        name = CountryName(
          common = "Georgia",
          official = "Georgia",
        ),
        emojiFlag = "🇬🇪",
        cca3 = "GEO",
      ),
    )

    declare<HttpClientEngine> {
      MockEngine { request ->
        respond(
          content = ByteReadChannel(body),
          status = HttpStatusCode.OK,
          headers = headersOf(HttpHeaders.ContentType, "application/json"),
        )
      }
    }

    val response = client.getCountryHeaders()
    assertThat(response).isInstanceOf(Results.Successful::class)

    assertThat((response as Results.Successful<List<CountryHeader>>).data)
      .isEqualTo(expectedResult)
  }

  @Test
  fun `By giving a successful details JSON response check correct parse and result`() = runTest {
    @FormatLanguage("json")
    val body = """
        [{
          "flags": {
            "png": "https://flagcdn.com/w320/au.png",
            "svg": "https://flagcdn.com/au.svg",
            "alt": "The flag of Australia has a dark blue field. It features the flag of the United Kingdom — the Union Jack — in the canton, beneath which is a large white seven-pointed star. A representation of the Southern Cross constellation, made up of one small five-pointed and four larger seven-pointed white stars, is situated on the fly side of the field."
          },
          "name": {
            "common": "Australia",
            "official": "Commonwealth of Australia",
            "nativeName": {
              "eng": {
                "official": "Commonwealth of Australia",
                "common": "Australia"
              }
            }
          },
          "cca3": "AUS",
          "currencies": {
            "AUD": {
              "name": "Australian dollar",
              "symbol": "$"
            }
          },
          "capital": [
            "Canberra"
          ],
          "languages": {
            "eng": "English"
          },
          "flag": "🇦🇺",
          "population": 25687041,
          "continents": [
            "Oceania"
          ]
        }]
    """.trimIndent()

    val expectedResult = DetailedCountry(
      cca3 = "AUS",
      name = CountryName(
        common = "Australia",
        official = "Commonwealth of Australia",
      ),
      emojiFlag = "🇦🇺",
      flag = CountryFlag(
        svg = "https://flagcdn.com/au.svg",
        png = "https://flagcdn.com/w320/au.png",
        alt = "The flag of Australia has a dark blue field. It features the flag of the United Kingdom — the Union Jack — in the canton, beneath which is a large white seven-pointed star. A representation of the Southern Cross constellation, made up of one small five-pointed and four larger seven-pointed white stars, is situated on the fly side of the field.",
      ),
      capital = listOf("Canberra"),
      languages = mapOf("eng" to "English"),
      currencies = mapOf("AUD" to Currency(name = "Australian dollar", symbol = "$")),
      continents = listOf("Oceania"),
      population = 25687041U,
    )

    declare<HttpClientEngine> {
      MockEngine { request ->
        respond(
          content = ByteReadChannel(body),
          status = HttpStatusCode.OK,
          headers = headersOf(HttpHeaders.ContentType, "application/json"),
        )
      }
    }

    val response = client.getDetailedCountryByCca3(expectedResult.cca3)
    assertThat(response).isInstanceOf(Results.Successful::class)

    assertThat((response as Results.Successful<DetailedCountry>).data)
      .isEqualTo(expectedResult)
  }

  @Test
  fun `If the server is not available expect failure result`() = runTest {
    val expectedStatusCode = HttpStatusCode.InternalServerError

    @FormatLanguage("json")
    val body = """
            { "message": "Server error" }
    """.trimIndent()

    declare<HttpClientEngine> {
      MockEngine { request ->
        respond(
          status = expectedStatusCode,
          content = ByteReadChannel(body),
        )
      }
    }

    val result = client.getCountryHeaders()

    assertThat(result)
      .isInstanceOf(Results.Failures.HttpStatusCode::class)

    assertThat((result as Results.Failures.HttpStatusCode).code)
      .isEqualTo(expectedStatusCode.value)
  }

  @Test
  fun `If the client made a wrong request expect failure result`() = runTest {
    val expectedStatusCode = HttpStatusCode.Unauthorized

    @FormatLanguage("json")
    val body = """
            { "message": "Unauthorized." }
    """.trimIndent()

    declare<HttpClientEngine> {
      MockEngine { request ->
        respond(
          status = expectedStatusCode,
          content = ByteReadChannel(body),
        )
      }
    }

    val result = client.getCountryHeaders()

    assertThat(result)
      .isInstanceOf(Results.Failures.HttpStatusCode::class)

    assertThat((result as Results.Failures.HttpStatusCode).code)
      .isEqualTo(expectedStatusCode.value)
  }

  @AfterTest
  fun afterTest() {
    stopKoin()
  }
}
