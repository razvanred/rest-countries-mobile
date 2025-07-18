// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class RootConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) = with(target) {
    configureSpotless()
  }
}
