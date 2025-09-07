// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.mappers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.res.stringResource
import red.razvan.restcountries.android.compose.app.R
import red.razvan.restcountries.data.models.NetworkFailure

fun interface NetworkFailureToMessageMapper {
  @Composable
  fun map(failure: NetworkFailure): String
}

private object DefaultNetworkFailureToMessageMapper : NetworkFailureToMessageMapper {
  @Composable
  override fun map(failure: NetworkFailure) = when (failure) {
    is NetworkFailure.WithHttpStatusCode -> {
      if (failure.isServerDown) {
        stringResource(R.string.network_failure_server_issues_message, failure.code)
      } else {
        stringResource(R.string.network_failure_client_issues_message, failure.code)
      }
    }
    is NetworkFailure.Undefined -> {
      stringResource(R.string.network_failure_unknown_issues_message)
    }
  }
}

val LocalNetworkFailureToMessageMapper = staticCompositionLocalOf<NetworkFailureToMessageMapper> {
  DefaultNetworkFailureToMessageMapper
}
