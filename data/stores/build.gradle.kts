// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.android")
  id("red.razvan.restcountries.android.library")
}

android {
  namespace = "red.razvan.restcountries.data.stores"
}

dependencies {
  api(projects.data.models)
  api(libs.koin.android)
  api(libs.store)

  implementation(projects.data.db)
  implementation(projects.data.remote)
  implementation(projects.core.utils)
  implementation(projects.commons.kotlinxCoroutines)
  implementation(projects.core.kotlinxCoroutines)
}
