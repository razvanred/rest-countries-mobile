// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CountryHeaderContinentCrossRefDao {

  @Insert
  suspend fun insert(refs: List<CountryHeaderContinentCrossRef>)
}
