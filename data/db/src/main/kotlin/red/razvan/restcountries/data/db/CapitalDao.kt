// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CapitalDao {

  @Upsert
  suspend fun upsert(capitals: List<Capital>)

  @Query(
    """
            SELECT *
            FROM capital
            WHERE name IN (
                SELECT capital_name
                FROM country_header_capital_cross_ref
                WHERE country_header_id = :countryId
            )
        """,
  )
  fun observeByCountryId(countryId: CountryId): Flow<List<Capital>>

  @VisibleForTesting
  @Query("SELECT * FROM capital")
  suspend fun getAll(): List<Capital>
}
