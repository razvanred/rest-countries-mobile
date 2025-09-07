// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.design

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun RefreshDropdownMenuItem(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  DropdownMenuItem(
    text = {
      Text(text = stringResource(R.string.refresh_item_label))
    },
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Refresh,
        contentDescription = null,
      )
    },
    onClick = onClick,
    modifier = modifier,
  )
}
