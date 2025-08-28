// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

sealed interface InvokeStatus<out T, out E> {
  data object InProgress : InvokeStatus<Nothing, Nothing>

  data class Successful<out T>(val data: T) : InvokeStatus<T, Nothing>

  data class Failure<out E>(val error: E) : InvokeStatus<Nothing, E>
}
