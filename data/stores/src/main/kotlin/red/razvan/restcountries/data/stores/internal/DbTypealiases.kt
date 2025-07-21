// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.stores.internal

import red.razvan.restcountries.data.db.Capital
import red.razvan.restcountries.data.db.Continent
import red.razvan.restcountries.data.db.CountryDetails
import red.razvan.restcountries.data.db.CountryHeader
import red.razvan.restcountries.data.db.CountryHeaderCapitalCrossRef
import red.razvan.restcountries.data.db.CountryHeaderContinentCrossRef
import red.razvan.restcountries.data.db.CountryHeaderCurrencyCrossRef
import red.razvan.restcountries.data.db.CountryHeaderLanguageCrossRef
import red.razvan.restcountries.data.db.CountryId
import red.razvan.restcountries.data.db.Currency
import red.razvan.restcountries.data.db.CurrencyId
import red.razvan.restcountries.data.db.Language
import red.razvan.restcountries.data.db.LanguageId

internal typealias DbCountryHeader = CountryHeader
internal typealias DbCountryDetails = CountryDetails
internal typealias DbCountryId = CountryId
internal typealias DbCapital = Capital
internal typealias DbContinent = Continent
internal typealias DbLanguageId = LanguageId
internal typealias DbLanguage = Language
internal typealias DbCurrency = Currency
internal typealias DbCurrencyId = CurrencyId
internal typealias DbCountryHeaderLanguageCrossRef = CountryHeaderLanguageCrossRef
internal typealias DbCountryHeaderCurrencyCrossRef = CountryHeaderCurrencyCrossRef
internal typealias DbCountryHeaderCapitalCrossRef = CountryHeaderCapitalCrossRef
internal typealias DbCountryHeaderContinentCrossRef = CountryHeaderContinentCrossRef
