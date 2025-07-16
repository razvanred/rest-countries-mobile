// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.gradle

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless() {
  val ktlintVersion = libs.findVersion("ktlint").get().requiredVersion

  with(pluginManager) {
    apply("com.diffplug.spotless")
  }

  spotless {
    kotlin {
      target("**/*.kt")
      targetExclude("${layout.buildDirectory}/**/*.kt")

      licenseHeaderFile(rootProject.file("spotless/copyright.txt"))

      ktlint(ktlintVersion)
        .customRuleSets(
          listOf(
            libs.findLibrary("ktlint-compose-rules").get().get().toString(),
          ),
        )
        .editorConfigOverride(
          mapOf(
            "ktlint_standard_filename" to "disabled",
            "ktlint_compose_compositionlocal-allowlist" to "disabled",
            "ktlint_compose_lambda-param-in-effect" to "disabled",
          ),
        )
    }
    kotlinGradle {
      target("**/*.kts")
      targetExclude("${layout.buildDirectory}/**/*.kts")

      licenseHeaderFile(rootProject.file("spotless/copyright.txt"), "(^(?![\\/ ]\\**).*$)")

      ktlint(ktlintVersion)
    }
  }
}

private fun Project.spotless(action: SpotlessExtension.() -> Unit) = extensions.configure<SpotlessExtension>(action)
