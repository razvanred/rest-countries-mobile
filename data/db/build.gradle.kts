// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.android.library")
  id("red.razvan.restcountries.kotlin.multiplatform")
  alias(libs.plugins.ksp)
}

android {
  namespace = "red.razvan.restcountries.data.db"

  defaultConfig {
    testInstrumentationRunner = "red.razvan.restcountries.testresources.android.koin.InstrumentationTestRunner"
  }
}

kotlin {
  androidTarget()
  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(libs.koin.core)
        api(libs.room.runtime)

        implementation(projects.commons.intellijAnnotations)
        implementation(libs.sqlite.bundled)
      }
    }

    val androidMain by getting {
      dependencies {
        api(libs.koin.android)
        implementation(libs.room.ktx)
      }
    }

    val androidInstrumentedTest by getting {
      dependencies {
        implementation(projects.testResources.androidKoin)
        implementation(libs.androidx.test.ext.junit.ktx)

        implementation(libs.assertk)
        implementation(libs.assertk.coroutines)

        implementation(libs.kotlinx.coroutines.test)

        implementation(libs.turbine)

        implementation(libs.koin.test)
        implementation(libs.koin.test.junit4)
      }
    }
  }
}

dependencies {
  add("kspAndroid", libs.room.compiler)
  add("kspIosArm64", libs.room.compiler)
  add("kspIosSimulatorArm64", libs.room.compiler)
}
