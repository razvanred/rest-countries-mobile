// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContinentDao {

  @Upsert
  suspend fun upsert(continents: List<Continent>)

  @Query(
    """
            SELECT *
            FROM continent
            WHERE name IN (
                SELECT continent_name
                FROM country_continent_cross_ref
                WHERE country_header_id = :countryId
            )
        """,
  )
  fun observeByCountryId(countryId: CountryId): Flow<List<Continent>>
}
