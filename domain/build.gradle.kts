// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
  id("red.razvan.restcountries.android.library")
}

kotlin {
  androidTarget()
  iosSimulatorArm64()
  iosArm64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(libs.koin.core)
        api(projects.data.models)
        implementation(projects.data.stores)
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
  namespace = "red.razvan.restcountries.domain"
}
