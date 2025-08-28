// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.data.stores.internal

import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.FetcherResult
import red.razvan.restcountries.data.models.NetworkFailure

internal fun <Key : Any, Network : Any> Fetcher.Companion.ofRemoteResult(
  fetch: suspend (Key) -> RemoteResult<Network>,
): Fetcher<Key, Network> = Fetcher.ofResult { key ->
  when (val result = fetch(key)) {
    is SuccessfulRemoteResult -> {
      FetcherResult.Data(value = result.data)
    }
    is FailureRemoteResult -> {
      FetcherResult.Error.Custom(error = result.toNetworkFailure())
    }
  }
}

private fun FailureRemoteResult.toNetworkFailure(): NetworkFailure = when (this) {
  is HttpStatusCodeFailureRemoteResult -> {
    NetworkFailure.WithHttpStatusCode(
      code = code,
      exception = exception,
    )
  }
  is UndefinedFailureRemoteResult -> {
    NetworkFailure.Undefined(exception = exception)
  }
}
