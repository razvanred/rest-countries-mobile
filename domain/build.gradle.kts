// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.android")
  id("red.razvan.restcountries.android.library")
}

android {
  namespace = "red.razvan.restcountries.domain"
}

dependencies {
  api(libs.koin.android)
  api(projects.data.models)
  implementation(projects.data.stores)
}
