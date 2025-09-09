// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.testresources.android.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import red.razvan.restcountries.android.domain.ArtifactsGroup
import red.razvan.restcountries.android.domain.ObserveArtifacts

class SampleDataObserveArtifacts(
  private val data: List<ArtifactsGroup> = SampleData.Artifacts.All,
  private val emitDelayInMillis: Long = 0L,
) : ObserveArtifacts {

  override fun invoke(): Flow<List<ArtifactsGroup>> = flow {
    delay(emitDelayInMillis)
    emit(data)
  }
}
