// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
}

kotlin {
  jvm()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(projects.android.domain)
      }
    }
  }
}
