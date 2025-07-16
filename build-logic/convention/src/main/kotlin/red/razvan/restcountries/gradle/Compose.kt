// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.gradle

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
  with(commonExtension) {
    buildFeatures {
      compose = true
    }

    composeCompiler {
      if (project.providers.gradleProperty("restcountries.enableComposeCompilerReports").isPresent) {
        val composeReports = layout.buildDirectory.map { it.dir("reports").dir("compose") }
        reportsDestination.set(composeReports)
        metricsDestination.set(composeReports)
      }

      stabilityConfigurationFiles.add(project.layout.projectDirectory.file("compose-stability.conf"))
    }

    dependencies {
      val bom = libs.findLibrary("androidx-compose-bom").get()
      "implementation"(platform(bom))
      "androidTestImplementation"(platform(bom))
    }
  }
}

private fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) {
  extensions.configure<ComposeCompilerGradlePluginExtension>(block)
}
