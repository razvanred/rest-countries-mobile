// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.gradle

import app.cash.licensee.LicenseeExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class LicenseeConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("app.cash.licensee")
      }

      configure<LicenseeExtension> {
        allow("Apache-2.0")
        allow("MIT")
        allowUrl("https://opensource.org/license/mit")

        bundleAndroidAsset.set(true)
      }
    }
  }
}
