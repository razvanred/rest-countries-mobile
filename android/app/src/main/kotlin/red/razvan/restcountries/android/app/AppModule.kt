// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.app

import com.chuckerteam.chucker.api.ChuckerInterceptor
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import red.razvan.restcountries.core.kotlinx.coroutines.AppCoroutineDispatchers

val AppModule = module {
  single<Interceptor> {
    ChuckerInterceptor(androidContext())
  }

  single {
    AppCoroutineDispatchers(
      databaseRead = Dispatchers.IO,
      databaseWrite = Dispatchers.IO.limitedParallelism(1),
      io = Dispatchers.IO,
      computation = Dispatchers.Default,
      main = Dispatchers.Main,
    )
  }
}
