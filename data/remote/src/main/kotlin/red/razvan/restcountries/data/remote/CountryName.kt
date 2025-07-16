// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CountryName(
  val common: String,
  val official: String,
)
