// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryHeaderDao {

  @Insert
  suspend fun insert(headers: List<CountryHeader>)

  @Insert
  suspend fun insert(header: CountryHeader)

  @Query("DELETE FROM country_header")
  suspend fun deleteAll()

  @Query("DELETE FROM country_header WHERE id = :id")
  suspend fun deleteById(id: red.razvan.restcountries.data.db.CountryId)

  @Upsert
  suspend fun upsert(country: CountryHeader)

  @Query(
    """
        SELECT
            *
        FROM country_header
        ORDER BY official_name ASC
    """,
  )
  fun observeAll(): Flow<List<CountryHeader>>

  @Query("SELECT * FROM country_header WHERE id = :id")
  fun observeByIdOrNull(id: red.razvan.restcountries.data.db.CountryId): Flow<CountryHeader?>
}
