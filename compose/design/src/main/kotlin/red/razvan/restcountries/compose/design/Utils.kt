// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.design

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun PaddingValues.toContainerAndContentPadding(): Pair<PaddingValues, PaddingValues> {
  val layoutDirection = LocalLayoutDirection.current
  return Pair(
    first = PaddingValues(
      top = calculateTopPadding(),
    ),
    second = PaddingValues(
      top = 0.dp,
      start = calculateStartPadding(layoutDirection),
      end = calculateEndPadding(layoutDirection),
      bottom = calculateBottomPadding(),
    ),
  )
}
