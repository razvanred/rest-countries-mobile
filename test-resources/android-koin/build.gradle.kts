// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.android")
  id("red.razvan.restcountries.android.library")
}

android {
  namespace = "red.razvan.restcountries.testresources.android.koin"
}

dependencies {
  api(libs.koin.android)
  api(libs.androidx.test.runner)
}
