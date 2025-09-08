// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
  id("red.razvan.restcountries.android.library")
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  androidTarget()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(libs.kotlinx.coroutines.core)
        implementation(projects.core.kotlinxCoroutines)
        implementation(libs.kotlinx.serialization.json)
      }
    }

    val androidMain by getting {
      dependencies {
        api(libs.koin.android)
      }
    }
  }
}

android {
  namespace = "red.razvan.restcountries.android.domain"
}
