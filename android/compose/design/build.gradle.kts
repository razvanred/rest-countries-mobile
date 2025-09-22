// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.android.library")
  id("red.razvan.restcountries.android.library.compose")
  id("red.razvan.restcountries.kotlin.android")
  alias(libs.plugins.paparazzi)
}

android {
  namespace = "red.razvan.restcountries.android.compose.design"
}

dependencies {
  api(libs.androidx.compose.material3)

  implementation(libs.androidx.compose.material.icons.core)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)

  testImplementation(libs.junit)
}
