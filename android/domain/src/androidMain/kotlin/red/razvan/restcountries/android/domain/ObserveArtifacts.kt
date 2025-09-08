// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.domain

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import red.razvan.restcountries.core.kotlinx.coroutines.AppCoroutineDispatchers

fun interface ObserveArtifacts {
  operator fun invoke(): Flow<List<ArtifactsGroup>>
}

@Serializable
internal data class SerializableArtifact(
  override val groupId: String,
  override val artifactId: String,
  override val version: String,
  override val spdxLicenses: List<SerializableSpdxLicense>?,
  override val name: String?,
  override val scm: SerializableScm?,
) : Artifact

@Serializable
internal data class SerializableSpdxLicense(
  override val identifier: String,
  override val name: String,
  override val url: String,
) : SpdxLicense

@Serializable
internal data class SerializableScm(
  override val url: String,
) : Scm

@OptIn(ExperimentalSerializationApi::class)
internal class DefaultObserveArtifacts(
  private val dispatchers: AppCoroutineDispatchers,
  private val context: Context,
) : ObserveArtifacts {

  private val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
  }

  override fun invoke(): Flow<List<ArtifactsGroup>> = flow {
    val groups = withContext(dispatchers.computation) {
      val artifacts: List<SerializableArtifact> = withContext(dispatchers.io) {
        json.decodeFromStream(context.assets.open("app/cash/licensee/artifacts.json"))
      }

      artifacts
        .groupBy { it.groupId }
        .map { (groupId, artifacts) ->
          ArtifactsGroup(
            id = groupId,
            artifacts = artifacts.sortedBy { it.artifactId },
          )
        }
        .sortedBy { it.id }
    }

    emit(groups)
  }
}
