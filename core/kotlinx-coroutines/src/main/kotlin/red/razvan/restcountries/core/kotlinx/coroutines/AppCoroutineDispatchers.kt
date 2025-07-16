// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.core.kotlinx.coroutines

import kotlin.coroutines.CoroutineContext

data class AppCoroutineDispatchers(
  val databaseWrite: CoroutineContext,
  val databaseRead: CoroutineContext,
  val io: CoroutineContext,
  val computation: CoroutineContext,
  val main: CoroutineContext,
)
