// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.Interceptor
import org.koin.dsl.module

internal actual val PlatformModule = module {
  single<HttpClientEngine> {
    OkHttp.create {
      getAll<Interceptor>().forEach(::addInterceptor)
    }
  }
}
