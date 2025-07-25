// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CountryHeaderLanguageCrossRefDao {

  @Insert
  suspend fun insert(refs: List<CountryHeaderLanguageCrossRef>)

  @Insert
  suspend fun insert(ref: CountryHeaderLanguageCrossRef)
}
