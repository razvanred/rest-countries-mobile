// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.models

sealed interface NetworkFailure {
  val exception: Exception?
}

object NetworkFailures {
  data class HttpStatusCodeFailureResult(
    val code: Int,
    override val exception: Exception?,
  ) : NetworkFailure {

    /**
     * Checks whether the status code is in the 500..599 range
     */
    val isServerDown: Boolean
      get() = code in 500..599
  }

  @JvmInline
  value class Undefined(override val exception: Exception?) : NetworkFailure
}
