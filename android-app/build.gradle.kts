// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.android.application")
  id("red.razvan.restcountries.android.application.compose")
  id("red.razvan.restcountries.kotlin.android")
}

android {
  namespace = "red.razvan.restcountries.android.app"

  signingConfigs {
    getByName("debug") {
      storeFile = file("debug.jks")
      storePassword = "android"
      keyAlias = "debug"
      keyPassword = "android"
    }
  }

  defaultConfig {
    applicationId = "red.razvan.restcountries.android.app"
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      signingConfig = signingConfigs.getByName("debug")
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
    debug {
      signingConfig = signingConfigs.getByName("debug")
    }
  }
}

dependencies {
  implementation(libs.kotlinx.coroutines.android)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)

  implementation(libs.koin.android)

  implementation(libs.android.material)

  implementation(projects.compose.app)

  implementation(projects.core.kotlinxCoroutines)

  debugImplementation(libs.chucker)
  releaseImplementation(libs.chucker.noop)
}
