// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

@file:Suppress("UnstableApiUsage")

pluginManagement {
  includeBuild("build-logic")

  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "rest-countries-mobile"

include(":android:app")

include(":android:compose:app")
include(":android:compose:design")

include(":data:remote")

include(":data:models")

include(":data:stores")

include(":data:db")

include(":core:kotlinx-coroutines")

include(":domain")

include(":core:utils")

include(":commons:kotlinx-coroutines")
include(":commons:intellij-annotations")

include(":test-resources:domain")
include(":test-resources:android-koin")
