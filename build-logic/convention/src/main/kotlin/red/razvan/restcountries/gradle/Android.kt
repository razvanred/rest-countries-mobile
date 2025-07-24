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

private fun Project.android(action: BaseExtension.() -> Unit) = extensions.configure<BaseExtension>(action)
