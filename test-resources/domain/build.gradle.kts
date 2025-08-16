// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
  id("red.razvan.restcountries.android.library")
}

android {
  namespace = "red.razvan.restcountries.testsources.domain"
}

kotlin {
  androidTarget()
  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(projects.domain)
      }
    }
  }
}
