// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.domain

interface Artifact {
  val groupId: String
  val artifactId: String
  val version: String
  val spdxLicenses: List<SpdxLicense>?
  val name: String?
  val scm: Scm?
}

interface SpdxLicense {
  val identifier: String
  val name: String
  val url: String
}

interface Scm {
  val url: String
}
