// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
}

kotlin {
  jvm()
  iosArm64()
  iosSimulatorArm64()
}
