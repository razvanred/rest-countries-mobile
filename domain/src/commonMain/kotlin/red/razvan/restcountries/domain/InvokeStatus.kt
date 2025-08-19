// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import kotlin.jvm.JvmInline

sealed interface InvokeStatus<out T, out E>

object InvokeStatuses {

  data object InProgress : InvokeStatus<Nothing, Nothing>

  @JvmInline
  value class Successful<out T>(val data: T) : InvokeStatus<T, Nothing>

  @JvmInline
  value class Failure<out E>(val error: E) : InvokeStatus<Nothing, E>
}
