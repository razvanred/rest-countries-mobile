// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
  id("red.razvan.restcountries.android.library")
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  jvm()
  androidTarget()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(libs.kotlinx.coroutines.core)
        implementation(projects.core.kotlinxCoroutines)
      }
    }

    val androidMain by getting {
      dependencies {
        api(libs.koin.android)
        implementation(libs.kotlinx.serialization.json)
      }
    }
  }
}

android {
  namespace = "red.razvan.restcountries.android.domain"
}
