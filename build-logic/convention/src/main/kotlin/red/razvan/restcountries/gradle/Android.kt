// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.gradle

import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroid() {
  android {
    compileSdkVersion(36)

    defaultConfig {
      minSdk = 24
      targetSdk = 36
    }

    compileOptions {
      isCoreLibraryDesugaringEnabled = true
    }

    @Suppress("UnstableApiUsage")
    with(testOptions.managedDevices.allDevices) {
      create<ManagedVirtualDevice>("pixel6api35") {
        device = "Pixel 6"
        apiLevel = 35
        systemImageSource = "aosp-atd"
      }
    }
  }

  configureJava()

  dependencies {
    "coreLibraryDesugaring"(libs.findLibrary("android-tools-desugar").get())
  }
}

data class AndroidAppVersion(
  val code: Int,
  val name: String,
) {
  companion object {

    /**
     * @param major Major version
     * @param minor Minor version (up to 99)
     * @param patch Patch version (up to 99)
     * @param build For dogfood builds, public betas, etc. (up to 9)
     */
    fun build(
      major: Int,
      minor: Int,
      patch: Int,
      build: Int = 0,
    ): AndroidAppVersion {
      require(major >= 0) { "Invalid major version number: $major" }
      require(minor in 0..99) { "Invalid minor version number: $minor" }
      require(patch in 0..99) { "Invalid patch version number: $patch" }
      require(build in 0..9) { "Invalid build version number: $build" }

      return AndroidAppVersion(
        code = major * 1_00_00_0 + minor * 1_00_0 + patch * 1_0 + build,
        name = "$major.$minor.$patch",
      )
    }
  }
}

private fun Project.android(action: BaseExtension.() -> Unit) = extensions.configure<BaseExtension>(action)
