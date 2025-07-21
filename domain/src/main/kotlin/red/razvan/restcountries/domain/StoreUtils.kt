// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import red.razvan.restcountries.data.models.NetworkFailure

internal fun <Key : Any, Output : Any> Store<Key, Output>.streamRequiredData(
  request: StoreReadRequest<Key>,
) = stream(request = request)
  .filter { it is StoreReadResponse.Data<Output> }
  .map { it.requireData() }

internal fun <Key : Any, Output : Any> Store<Key, Output>.streamRefreshStatus(
  key: Key,
): Flow<InvokeStatus<Unit, NetworkFailure>> = stream(StoreReadRequest.fresh(key))
  .map { response ->
    when (response) {
      is StoreReadResponse.Data<Output> -> InvokeStatuses.Successful(data = Unit)
      is StoreReadResponse.Error.Custom<*> -> {
        val error = response.error
        if (error !is NetworkFailure) {
          error("Store error $error not expected")
        }

        InvokeStatuses.Failure(error)
      }
      StoreReadResponse.Initial, is StoreReadResponse.Loading -> InvokeStatuses.InProgress
      is StoreReadResponse.NoNewData,
      is StoreReadResponse.Error.Message,
      is StoreReadResponse.Error.Exception,
      -> {
        error("Store response $this not expected")
      }
    }
  }
