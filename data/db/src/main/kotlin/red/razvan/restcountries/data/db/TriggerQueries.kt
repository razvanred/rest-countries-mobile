// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.intellij.lang.annotations.Language

internal object TriggerQueries {

  @Language("RoomSql")
  private const val DELETE_ORPHANED_CAPITAL = """
    CREATE TRIGGER delete_orphaned_capital
    AFTER DELETE ON country_header_capital_cross_ref
    BEGIN
        DELETE FROM capital
        WHERE name = OLD.capital_name
        AND NOT EXISTS (
            SELECT 1
            FROM country_header_capital_cross_ref
            WHERE capital_name = OLD.capital_name
        );
    END;
  """

  @Language("RoomSql")
  private const val DELETE_ORPHANED_CONTINENTS = """
    CREATE TRIGGER delete_orphaned_continents
    AFTER DELETE ON country_header_continent_cross_ref
    BEGIN
        DELETE FROM continent
        WHERE name = OLD.continent_name
        AND NOT EXISTS (
            SELECT 1
            FROM country_header_continent_cross_ref
            WHERE continent_name = OLD.continent_name
        );
    END;
  """

  @Language("RoomSql")
  private const val DELETE_ORPHANED_CURRENCIES = """
    CREATE TRIGGER delete_orphaned_curriencies
    AFTER DELETE ON country_header_currency_cross_ref
    BEGIN
        DELETE FROM currency
        WHERE id = OLD.currency_id
        AND NOT EXISTS (
            SELECT 1
            FROM country_header_currency_cross_ref
            WHERE currency_id = OLD.currency_id
        );
    END;
  """

  @Language("RoomSql")
  private const val DELETE_ORPHANED_LANGUAGES = """
    CREATE TRIGGER delete_orphaned_languages
    AFTER DELETE ON country_header_language_cross_ref
    BEGIN
        DELETE FROM language
        WHERE id = OLD.language_id
        AND NOT EXISTS (
            SELECT 1
            FROM country_header_language_cross_ref
            WHERE language_id = OLD.language_id
        );
    END;
  """

  fun <T : RoomDatabase> RoomDatabase.Builder<T>.addTriggerQueries() = addCallback(
    object : RoomDatabase.Callback() {
      override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        db.execSQL(DELETE_ORPHANED_CAPITAL)
        db.execSQL(DELETE_ORPHANED_LANGUAGES)
        db.execSQL(DELETE_ORPHANED_CONTINENTS)
        db.execSQL(DELETE_ORPHANED_CURRENCIES)
      }
    },
  )
}
