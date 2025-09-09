// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.domain

import kotlinx.coroutines.flow.Flow

fun interface ObserveArtifacts {
  operator fun invoke(): Flow<List<ArtifactsGroup>>
}
