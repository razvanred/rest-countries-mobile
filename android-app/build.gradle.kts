// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

import red.razvan.restcountries.gradle.AndroidAppVersion

plugins {
  id("red.razvan.restcountries.android.application")
  id("red.razvan.restcountries.android.application.compose")
  id("red.razvan.restcountries.kotlin.android")
  alias(libs.plugins.localproperties)
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

    file("secrets/release.jks")
      .takeIf(File::exists)
      ?.let { releaseStoreFile ->
        create("release") {
          storeFile = releaseStoreFile
          storePassword = properties["ANDROID_RELEASE_KEYSTORE_PASSWORD"]?.toString().orEmpty()
          keyAlias = "rest-countries"
          keyPassword = properties["ANDROID_RELEASE_KEY_PASSWORD"]?.toString().orEmpty()
        }
      }
  }

  defaultConfig {
    applicationId = "red.razvan.restcountries"

    AndroidAppVersion.build(major = 1, minor = 0, patch = 0).let { (code, name) ->
      versionCode = code
      versionName = name
    }

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      signingConfig = signingConfigs.findByName("release") ?: signingConfigs["debug"]
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
    debug {
      signingConfig = signingConfigs["debug"]
      applicationIdSuffix = ".debug"
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
