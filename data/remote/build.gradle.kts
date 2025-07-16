// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.android")
  id("red.razvan.restcountries.android.library")
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "red.razvan.restcountries.data.remote"
}

dependencies {
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.ktor.serialization.kotlinx.json)
  implementation(libs.ktor.client.core)
  implementation(libs.ktor.client.okhttp)
  implementation(libs.ktor.client.logging)
  implementation(libs.ktor.client.contentnegotiation)
  api(libs.koin.android)

  testImplementation(libs.ktor.client.mock)
  testImplementation(libs.assertk)
  testImplementation(libs.assertk.coroutines)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.koin.test)
  testImplementation(libs.koin.test.junit4)
}
