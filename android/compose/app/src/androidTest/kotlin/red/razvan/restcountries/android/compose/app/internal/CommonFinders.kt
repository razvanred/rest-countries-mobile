// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import red.razvan.restcountries.android.compose.design.R as DesignR

class CommonFinders(
  private val composeTestRule: ComposeTestRule,
  private val context: Context,
) {

  fun findPullToRefreshIndicator() = composeTestRule
    .onNodeWithTag(testTag = "pull-to-refresh-indicator")

  fun findSnackbar() = composeTestRule
    .onNodeWithTag(testTag = "snackbar")

  fun findMoreOptionsButton() = composeTestRule
    .onNodeWithContentDescription(
      label = context.getString(DesignR.string.more_options_button_cd),
    )
}
