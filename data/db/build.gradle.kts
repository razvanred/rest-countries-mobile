// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.android")
  id("red.razvan.restcountries.android.library")
  alias(libs.plugins.ksp)
}

android {
  namespace = "red.razvan.restcountries.data.db"

  defaultConfig {
    testInstrumentationRunner = "red.razvan.restcountries.testresources.android.koin.InstrumentationTestRunner"
  }
}

dependencies {
  api(libs.koin.android)
  api(libs.room.runtime)
  implementation(libs.room.ktx)
  ksp(libs.room.compiler)

  // region Testing
  androidTestImplementation(projects.testResources.androidKoin)
  androidTestImplementation(libs.androidx.test.ext.junit.ktx)

  androidTestImplementation(libs.assertk)
  androidTestImplementation(libs.assertk.coroutines)

  androidTestImplementation(libs.kotlinx.coroutines.test)

  androidTestImplementation(libs.turbine)

  androidTestImplementation(libs.koin.test)
  androidTestImplementation(libs.koin.test.junit4)
  // endregion
}
