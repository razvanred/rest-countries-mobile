// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import red.razvan.restcountries.data.db.TriggerQueries.addTriggerQueries

object AppleRoomAppDatabase {
  fun inMemoryDatabaseBuilder(): RoomDatabase.Builder<RoomAppDatabase> = Room
    .inMemoryDatabaseBuilder<RoomAppDatabase>()
    .addTriggerQueries()

  fun databaseBuilder(): RoomDatabase.Builder<RoomAppDatabase> = Room
    .databaseBuilder(name = "$documentDirectory/app.db")
}

@OptIn(ExperimentalForeignApi::class)
private val documentDirectory: String
  get() = NSFileManager.defaultManager.URLForDirectory(
    directory = NSDocumentDirectory,
    inDomain = NSUserDomainMask,
    appropriateForURL = null,
    create = false,
    error = null,
  )
    ?.path!!
