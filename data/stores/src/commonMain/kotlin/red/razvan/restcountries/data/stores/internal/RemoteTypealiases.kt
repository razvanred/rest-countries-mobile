// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.stores.internal

import red.razvan.restcountries.data.remote.CountryFlag
import red.razvan.restcountries.data.remote.CountryHeader
import red.razvan.restcountries.data.remote.CountryName
import red.razvan.restcountries.data.remote.Currency
import red.razvan.restcountries.data.remote.DetailedCountry
import red.razvan.restcountries.data.remote.Result
import red.razvan.restcountries.data.remote.Results

internal typealias RemoteCountryHeader = CountryHeader
internal typealias RemoteDetailedCountry = DetailedCountry
internal typealias RemoteCountryFlag = CountryFlag
internal typealias RemoteCurrency = Currency
internal typealias RemoteCountryName = CountryName
internal typealias RemoteResult<T> = Result<T>
internal typealias FailureRemoteResult = Results.Failure
internal typealias HttpStatusCodeFailureRemoteResult = Results.Failures.HttpStatusCode
internal typealias UndefinedFailureRemoteResult = Results.Failures.Undefined
internal typealias SuccessfulRemoteResult<T> = Results.Successful<T>
