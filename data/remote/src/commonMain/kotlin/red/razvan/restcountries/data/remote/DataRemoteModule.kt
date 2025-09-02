// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import io.ktor.client.plugins.logging.Logger as ClientLogger

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
        logger = object : ClientLogger {
          override fun log(message: String) {
            Logger.d("countries-ktor-client") { message }
          }
        }
        level = LogLevel.ALL // TODO avoid logging body on release
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
