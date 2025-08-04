// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.immediateTransaction
import androidx.room.useWriterConnection

fun interface RunDatabaseTransaction {
  suspend operator fun invoke(block: suspend () -> Unit)
}

internal class RunRoomDatabaseTransaction(
  private val database: RoomAppDatabase,
) : RunDatabaseTransaction {

  override suspend fun invoke(block: suspend () -> Unit) {
    // https://slack-chats.kotlinlang.org/t/23397234/why-roomdatabase-withtransaction-is-defined-in-roomdatabase-#de126391-effc-4507-a39c-90dcda5ff085
    database.useWriterConnection { transactor ->
      transactor.immediateTransaction { block() }
    }
  }
}
