// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.remote

import kotlin.jvm.JvmInline

sealed interface Result<out T>

object Results {

  @JvmInline
  value class Successful<out T>(val data: T) : Result<T>

  sealed interface Failure : Result<Nothing> {
    val exception: Exception?
  }

  object Failures {
    @JvmInline
    value class Undefined(override val exception: Exception?) : Failure

    data class HttpStatusCode(val code: Int, override val exception: Exception?) : Failure
  }
}
