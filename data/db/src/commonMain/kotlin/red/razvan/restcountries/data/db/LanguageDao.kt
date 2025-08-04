// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {

  @Upsert
  suspend fun upsert(languages: List<Language>)

  @Upsert
  suspend fun upsert(language: Language)

  @Query(
    """
            SELECT *
            FROM language
            WHERE id IN (
                SELECT language_id
                FROM country_header_language_cross_ref
                WHERE country_header_id = :countryId
            )
        """,
  )
  fun observeByCountryId(countryId: CountryId): Flow<List<Language>>

  @VisibleForTesting
  @Query("SELECT * FROM language")
  suspend fun getAll(): List<Language>
}
