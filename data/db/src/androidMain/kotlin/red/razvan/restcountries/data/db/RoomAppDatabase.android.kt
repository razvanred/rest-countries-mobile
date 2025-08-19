// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase.Builder

object AndroidRoomAppDatabase {
  fun inMemoryDatabaseBuilder(context: Context): Builder<RoomAppDatabase> = Room
    .inMemoryDatabaseBuilder(context, RoomAppDatabase::class.java)

  fun databaseBuilder(context: Context): Builder<RoomAppDatabase> = Room
    .databaseBuilder(context, RoomAppDatabase::class.java, "app.db")
}
