// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CountryFlag(
  val svg: String,
  val png: String,
  // TODO make it nullable, along with the DB and model
  //  Sometimes the field is missing from the response.
  val alt: String = "",
)
