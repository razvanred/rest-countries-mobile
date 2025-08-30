// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import android.util.Log
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.Logger
import okhttp3.Interceptor
import org.koin.dsl.module

internal actual val PlatformModule = module {
  single<HttpClientEngine> {
    OkHttp.create {
      getAll<Interceptor>().forEach(::addInterceptor)
    }
  }

  single<Logger> {
    AndroidLogger
  }
}

private object AndroidLogger : Logger {
  override fun log(message: String) {
    Log.i("HttpClient", message)
  }
}
