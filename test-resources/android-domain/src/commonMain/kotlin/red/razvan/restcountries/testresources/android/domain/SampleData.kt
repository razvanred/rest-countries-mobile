// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.testresources.android.domain

import red.razvan.restcountries.android.domain.Artifact
import red.razvan.restcountries.android.domain.ArtifactsGroup
import red.razvan.restcountries.android.domain.Scm
import red.razvan.restcountries.android.domain.SpdxLicense

object SampleData {

  object Artifacts {
    val AndroidxCompose = ArtifactsGroup(
      id = "androidx.compose",
      artifacts = listOf(
        DefaultArtifact(
          groupId = "androidx.compose",
          artifactId = "material3",
          version = "1.0.0",
          spdxLicenses = emptyList(),
          name = "AndroidX Compose Material",
          scm = DefaultScm("https://example.com"),
        ),
        DefaultArtifact(
          groupId = "androidx.compose",
          artifactId = "material-icons-extended",
          version = "1.3.0",
          spdxLicenses = emptyList(),
          name = "AndroidX Compose Material Icons Extended",
          scm = DefaultScm("https://example.com"),
        ),
      ),
    )

    val Insetter = ArtifactsGroup(
      id = "dev.chrisbanes.insetter",
      artifacts = listOf(
        DefaultArtifact(
          groupId = "dev.chrisbanes.insetter",
          artifactId = "insetter",
          version = "0.6.0",
          spdxLicenses = null,
          name = "Insetter",
          scm = DefaultScm("https://example.com"),
        ),
      ),
    )

    val All = listOf(AndroidxCompose, Insetter)
  }
}

private data class DefaultArtifact(
  override val groupId: String,
  override val artifactId: String,
  override val version: String,
  override val spdxLicenses: List<SpdxLicense>?,
  override val name: String?,
  override val scm: Scm?,
) : Artifact

@JvmInline
private value class DefaultScm(
  override val url: String,
) : Scm
