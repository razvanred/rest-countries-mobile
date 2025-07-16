// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }

  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
  }
}

rootProject.name = "build-logic"
include(":convention")
