// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    with(pluginManager) {
      apply("org.jetbrains.kotlin.multiplatform")
    }

    extensions.configure<KotlinMultiplatformExtension> {
      applyDefaultHierarchyTemplate()

      targets.configureEach {
        compilations.configureEach {
          compileTaskProvider.configure {
            compilerOptions {
              with(freeCompilerArgs) {
                add("-Xexpect-actual-classes")
              }
            }
          }
        }
      }
    }

    configureSpotless()
    configureJava()
  }
}
