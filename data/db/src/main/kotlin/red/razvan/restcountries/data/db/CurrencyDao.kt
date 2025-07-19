// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

  @Upsert
  suspend fun upsert(currencies: List<Currency>)

  @Query(
    """
            SELECT *
            FROM currency
            WHERE id IN (
                SELECT currency_id
                FROM country_header_currency_cross_ref
                WHERE country_header_id = :countryId
            )
        """,
  )
  fun observeByCountryId(countryId: CountryId): Flow<List<Currency>>
}
