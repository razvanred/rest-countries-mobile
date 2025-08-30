// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.parametersOf

interface CountriesApi {

  suspend fun getCountryHeaders(): Result<List<CountryHeader>>

  suspend fun getDetailedCountryByCca3(cca3: String): Result<DetailedCountry>
}

internal class KtorClientCountriesApi(
  private val client: HttpClient,
) : CountriesApi {

  override suspend fun getCountryHeaders(): Result<List<CountryHeader>> = runApiCall {
    client
      .get("v3.1/all") {
        url {
          parameters.appendAll(
            ParameterNames.FIELDS,
            listOf(
              FieldsParameterValues.CCA3,
              FieldsParameterValues.FLAG,
              FieldsParameterValues.NAME,
            ),
          )
        }
      }
      .body<List<CountryHeader>>()
  }

  override suspend fun getDetailedCountryByCca3(cca3: String): Result<DetailedCountry> = runApiCall {
    client
      .get("v3.1/alpha/$cca3") {
        parametersOf(
          ParameterNames.FIELDS,
          listOf(
            FieldsParameterValues.CCA3,
            FieldsParameterValues.FLAG,
            FieldsParameterValues.FLAGS,
            FieldsParameterValues.NAME,
            FieldsParameterValues.CURRENCIES,
            FieldsParameterValues.CONTINENTS,
            FieldsParameterValues.CAPITAL,
            FieldsParameterValues.LANGUAGES,
            FieldsParameterValues.POPULATION,
          ),
        )
      }
      .body<List<DetailedCountry>>()
      .first() // The API returns to us a list of countries anyway
  }

  private object ParameterNames {
    const val FIELDS = "fields"
  }

  private object FieldsParameterValues {
    const val FLAG = "flag"
    const val FLAGS = "flags"
    const val NAME = "name"
    const val CCA3 = "cca3"
    const val CURRENCIES = "currencies"
    const val LANGUAGES = "languages"
    const val POPULATION = "population"
    const val CONTINENTS = "continents"
    const val CAPITAL = "capital"
  }

  private companion object {
    suspend fun <T> runApiCall(block: suspend () -> T): Result<T> = try {
      Results.Successful(data = block())
    } catch (e: ClientRequestException) {
      Results.Failures.HttpStatusCode(code = e.response.status.value, exception = e)
    } catch (e: ServerResponseException) {
      Results.Failures.HttpStatusCode(code = e.response.status.value, exception = e)
    } catch (e: Exception) {
      Results.Failures.Undefined(exception = e)
    }
  }
}
