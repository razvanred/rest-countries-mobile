// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
  id("red.razvan.restcountries.android.library")
  alias(libs.plugins.skie)
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

    targets.withType<KotlinNativeTarget>().configureEach {
      binaries.framework {
        isStatic = true
        baseName = "TestResourcesDomainKt"
      }
    }
  }
}

skie {
  features {
    enableSwiftUIObservingPreview = true
  }
}
