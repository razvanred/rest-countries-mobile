// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
  id("red.razvan.restcountries.android.library")
}

kotlin {
  androidTarget()
  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(projects.data.models)
        api(libs.koin.core)
        api(libs.store)

        implementation(projects.data.db)
        implementation(projects.data.remote)
        implementation(projects.core.utils)
        implementation(projects.commons.kotlinxCoroutines)
        implementation(projects.core.kotlinxCoroutines)
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
  namespace = "red.razvan.restcountries.data.stores"
}
