// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("red.razvan.restcountries.kotlin.android")
  id("red.razvan.restcountries.android.library")
}

android {
  namespace = "red.razvan.restcountries.testsources.domain"
}

dependencies {
  api(projects.domain)
}
