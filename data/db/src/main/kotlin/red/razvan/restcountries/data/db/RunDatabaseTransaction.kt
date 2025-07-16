// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.withTransaction

fun interface RunDatabaseTransaction {
  suspend operator fun invoke(block: suspend () -> Unit)
}

internal class RunRoomDatabaseTransaction(
  private val database: RoomAppDatabase,
) : RunDatabaseTransaction {

  override suspend fun invoke(block: suspend () -> Unit) = database.withTransaction(block)
}
