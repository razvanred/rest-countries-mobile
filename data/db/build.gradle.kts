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
      }
    }

    val androidMain by getting {
      dependencies {
        api(libs.koin.android)
        implementation(libs.room.ktx)
      }
    }
  }
}

dependencies {
  add("kspAndroid", libs.room.compiler)
  add("kspIosArm64", libs.room.compiler)
  add("kspIosSimulatorArm64", libs.room.compiler)

  // region Testing
  androidTestImplementation(projects.testResources.androidKoin)
  androidTestImplementation(libs.androidx.test.ext.junit.ktx)

  androidTestImplementation(libs.assertk)
  androidTestImplementation(libs.assertk.coroutines)

  androidTestImplementation(libs.kotlinx.coroutines.test)

  androidTestImplementation(libs.turbine)

  androidTestImplementation(libs.koin.test)
  androidTestImplementation(libs.koin.test.junit4)
  // endregion
}
