// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import red.razvan.restcountries.android.compose.app.AppModule as ComposeAppModule

class RestCountriesApp : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@RestCountriesApp)
      androidLogger()
      modules(
        ComposeAppModule,
        AppModule,
      )
    }
  }
}
