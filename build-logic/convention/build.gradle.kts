// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  `kotlin-dsl`
  alias(libs.plugins.spotless)
}

spotless {
  kotlin {
    target("**/*.kt")
    ktlint(libs.versions.ktlint.get())
    licenseHeaderFile(rootProject.file("../spotless/copyright.txt"))
  }
  kotlinGradle {
    target("**/*.kts")
    ktlint(libs.versions.ktlint.get())
    licenseHeaderFile(rootProject.file("../spotless/copyright.txt"), "(^(?![\\/ ]\\**).*$)")
  }
}

java {
  toolchain {
    version = JavaLanguageVersion.of(21)
  }
}

dependencies {
  compileOnly(libs.android.gp)
  compileOnly(libs.kotlin.gp)
  compileOnly(libs.kotlin.compose.gp)
  compileOnly(libs.spotless.gp)
}

gradlePlugin {
  plugins {
    register("root") {
      id = "red.razvan.restcountries.root"
      implementationClass = "red.razvan.restcountries.gradle.RootConventionPlugin"
    }

    register("kotlinJvm") {
      id = "red.razvan.restcountries.kotlin.jvm"
      implementationClass = "red.razvan.restcountries.gradle.KotlinJvmConventionPlugin"
    }

    register("androidLibrary") {
      id = "red.razvan.restcountries.android.library"
      implementationClass = "red.razvan.restcountries.gradle.AndroidLibraryConventionPlugin"
    }

    register("androidApplication") {
      id = "red.razvan.restcountries.android.application"
      implementationClass = "red.razvan.restcountries.gradle.AndroidApplicationConventionPlugin"
    }

    register("kotlinAndroid") {
      id = "red.razvan.restcountries.kotlin.android"
      implementationClass = "red.razvan.restcountries.gradle.KotlinAndroidConventionPlugin"
    }

    register("androidApplicationCompose") {
      id = "red.razvan.restcountries.android.application.compose"
      implementationClass = "red.razvan.restcountries.gradle.AndroidApplicationComposeConventionPlugin"
    }

    register("androidLibraryCompose") {
      id = "red.razvan.restcountries.android.library.compose"
      implementationClass = "red.razvan.restcountries.gradle.AndroidLibraryComposeConventionPlugin"
    }
  }
}
