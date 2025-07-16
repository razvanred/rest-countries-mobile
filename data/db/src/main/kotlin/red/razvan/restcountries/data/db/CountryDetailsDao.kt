// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDetailsDao {

  @Insert
  suspend fun insert(details: CountryDetails)

  @Query("SELECT * FROM country_details WHERE id = :id")
  fun observeByIdOrNull(id: red.razvan.restcountries.data.db.CountryId): Flow<CountryDetails?>
}
