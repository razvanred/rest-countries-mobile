// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.testresources.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.InvokeStatus
import red.razvan.restcountries.data.models.InvokeStatuses
import red.razvan.restcountries.data.models.NetworkFailure
import red.razvan.restcountries.data.models.NetworkFailures
import red.razvan.restcountries.domain.RefreshDetailedCountryById

class SuccessfulRefreshDetailedCountryById(
  private val emitDelayInMillis: Long = 0L,
) : RefreshDetailedCountryById {

  override fun invoke(id: CountryId): Flow<InvokeStatus<Unit, NetworkFailure>> = flow {
    emit(InvokeStatuses.InProgress)
    delay(emitDelayInMillis)
    emit(InvokeStatuses.Successful(Unit))
  }
}

class FailingRefreshDetainedCountryById(
  private val failure: NetworkFailure = NetworkFailures.Undefined(null),
  private val failureEmitDelayInMillis: Long = 0L,
) : RefreshDetailedCountryById {

  override fun invoke(id: CountryId): Flow<InvokeStatus<Unit, NetworkFailure>> = flow {
    emit(InvokeStatuses.InProgress)
    delay(failureEmitDelayInMillis)
    emit(InvokeStatuses.Failure(failure))
  }
}
