// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.testresources.android.koin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@TestApplication)
    }
  }
}
