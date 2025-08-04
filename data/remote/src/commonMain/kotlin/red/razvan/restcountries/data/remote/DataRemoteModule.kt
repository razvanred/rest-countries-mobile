// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val DataRemoteModule = module {
  includes(PlatformModule)

  single<CountriesApi> {
    KtorClientCountriesApi(
      client = get(),
    )
  }

  single {
    HttpClient(get<HttpClientEngine>()) {
      defaultRequest {
        url {
          protocol = URLProtocol.HTTPS
          host = "restcountries.com"
        }
      }

      install(Logging) {
        logger = get<Logger>()
        level = LogLevel.ALL
      }

      install(ContentNegotiation) {
        json(
          Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            coerceInputValues = true
          },
        )
      }

      expectSuccess = true
    }
  }
}
