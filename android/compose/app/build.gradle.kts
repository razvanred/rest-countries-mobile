// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.android")
  id("red.razvan.restcountries.android.library")
  id("red.razvan.restcountries.android.library.compose")
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.kotlin.parcelize)
  alias(libs.plugins.paparazzi)
}

android {
  namespace = "red.razvan.restcountries.android.compose.app"

  defaultConfig {
    testInstrumentationRunner = "red.razvan.restcountries.testresources.android.koin.InstrumentationTestRunner"
  }
}

dependencies {
  implementation(projects.android.compose.design)

  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)

  implementation(libs.androidx.navigation.compose)
  implementation(libs.kotlinx.serialization.json)

  // region DI modules
  implementation(libs.koin.androidx.compose)
  implementation(libs.koin.compose.viewmodel)
  implementation(libs.koin.compose.viewmodel.navigation)
  implementation(projects.domain)
  // endregion

  // region Testing
  androidTestImplementation(libs.androidx.test.ext.junit.ktx)
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  androidTestImplementation(projects.testResources.domain)
  androidTestImplementation(libs.assertk)
  androidTestImplementation(projects.testResources.androidKoin)
  androidTestImplementation(libs.koin.test)
  androidTestImplementation(libs.koin.test.junit4)
  androidTestImplementation(libs.androidx.test.rules)
  androidTestImplementation(libs.androidx.espresso.core)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.test.manifest)

  testImplementation(libs.junit)

  testImplementation(libs.assertk)

  testImplementation(libs.kotlinx.coroutines.test)

  testImplementation(libs.turbine)

  testImplementation(libs.koin.test)
  testImplementation(libs.koin.test.junit4)

  testImplementation(libs.coil.test)

  testImplementation(projects.testResources.domain)
  // endregion

  implementation(libs.coil.compose)
  implementation(libs.coil.okhttp)
  implementation(libs.coil.svg)

  implementation(projects.core.utils)
}
