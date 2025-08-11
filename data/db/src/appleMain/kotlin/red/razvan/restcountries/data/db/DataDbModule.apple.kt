// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.RoomDatabase
import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope

internal actual fun Scope.getDatabaseBuilder(params: ParametersHolder): RoomDatabase.Builder<RoomAppDatabase> = AppleRoomAppDatabase.databaseBuilder()
