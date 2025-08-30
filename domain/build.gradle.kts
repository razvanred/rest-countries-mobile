// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  id("red.razvan.restcountries.kotlin.multiplatform")
  id("red.razvan.restcountries.android.library")
  alias(libs.plugins.skie)
}

kotlin {
  androidTarget()
  iosSimulatorArm64()
  iosArm64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(libs.koin.core)
        api(projects.data.models)
        implementation(projects.data.stores)
      }
    }

    val androidMain by getting {
      dependencies {
        api(libs.koin.android)
      }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
      binaries.framework {
        isStatic = true
        baseName = "DomainKt"
      }
    }
  }
}

skie {
  features {
    enableSwiftUIObservingPreview = true
  }
}

android {
  namespace = "red.razvan.restcountries.domain"
}
