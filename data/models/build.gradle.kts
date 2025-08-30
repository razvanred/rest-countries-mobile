// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
  alias(libs.plugins.skie)
}

kotlin {
  jvm()
  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    targets.withType<KotlinNativeTarget>().configureEach {
      binaries.framework {
        isStatic = true
        baseName = "ModelsKt"
      }
    }
  }
}
