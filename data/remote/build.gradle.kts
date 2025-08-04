// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
  id("red.razvan.restcountries.android.library")
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "red.razvan.restcountries.data.remote"
}

kotlin {
  androidTarget()
  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.ktor.serialization.kotlinx.json)
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.client.contentnegotiation)
        implementation(projects.commons.intellijAnnotations)
        api(libs.koin.core)
      }
    }

    val iosMain by getting {
      dependencies {
        implementation(libs.ktor.client.darwin)
      }
    }

    val commonTest by getting {
      dependencies {
        implementation(libs.kotlin.test)
        implementation(libs.ktor.client.mock)
        implementation(libs.assertk)
        implementation(libs.assertk.coroutines)
        implementation(libs.kotlinx.coroutines.test)
        implementation(libs.koin.test)
      }
    }

    val androidMain by getting {
      dependencies {
        api(libs.koin.android)
        implementation(libs.ktor.client.okhttp)
      }
    }
  }
}
