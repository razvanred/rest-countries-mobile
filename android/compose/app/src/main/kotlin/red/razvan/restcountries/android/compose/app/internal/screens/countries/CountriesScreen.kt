// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.countries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import red.razvan.restcountries.android.compose.app.R
import red.razvan.restcountries.android.compose.app.internal.mappers.LocalNetworkFailureToMessageMapper
import red.razvan.restcountries.android.compose.design.MoreOptionsVertButton
import red.razvan.restcountries.android.compose.design.RefreshDropdownMenuItem
import red.razvan.restcountries.android.compose.design.RestCountriesTheme
import red.razvan.restcountries.android.compose.design.toContainerAndContentPadding
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.CountryListItem

@Composable
internal fun CountriesScreen(
  onNavigateToCountryDetails: (CountryId) -> Unit,
  onNavigateToLicenses: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: CountriesScreenViewModel = koinViewModel(),
) {
  val state by viewModel
    .state
    .collectAsStateWithLifecycle(initialValue = CountriesScreenUiState.Empty)

  CountriesScreen(
    state = state,
    onRefresh = viewModel::refresh,
    modifier = modifier,
    onNavigateToCountryDetails = onNavigateToCountryDetails,
    onNetworkFailureMessageDismissal = viewModel::clearNetworkFailure,
    onDropdownMenuExpandedChange = viewModel::setDropdownMenuExpanded,
    onNavigateToLicenses = onNavigateToLicenses,
  )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun CountriesScreen(
  state: CountriesScreenUiState,
  onDropdownMenuExpandedChange: (Boolean) -> Unit,
  onRefresh: () -> Unit,
  onNetworkFailureMessageDismissal: () -> Unit,
  onNavigateToCountryDetails: (CountryId) -> Unit,
  onNavigateToLicenses: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val topAppBarScrollBehavior = TopAppBarDefaults
    .exitUntilCollapsedScrollBehavior()

  val snackbarHostState = remember { SnackbarHostState() }
  val pullToRefreshState = rememberPullToRefreshState()

  val swipeToDismissBoxState = rememberSwipeToDismissBoxState()

  if (state.networkFailure != null) {
    val message = LocalNetworkFailureToMessageMapper.current.map(state.networkFailure)
    val actionLabel = stringResource(R.string.retry_button_text)

    LaunchedEffect(message) {
      val actionResult = snackbarHostState.showSnackbar(message, actionLabel)

      if (actionResult == SnackbarResult.ActionPerformed) {
        onRefresh()
      } else if (actionResult == SnackbarResult.Dismissed) {
        onNetworkFailureMessageDismissal()
      }
    }
  }

  Scaffold(
    modifier = modifier
      .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
      .fillMaxSize(),
    topBar = {
      MediumFlexibleTopAppBar(
        title = {
          Text(text = stringResource(R.string.countries_list_screen_title))
        },
        scrollBehavior = topAppBarScrollBehavior,
        actions = {
          Box {
            MoreOptionsVertButton(
              onClick = {
                onDropdownMenuExpandedChange(!state.isDropdownMenuExpanded)
              },
            )

            DropdownMenu(
              expanded = state.isDropdownMenuExpanded,
              onDismissRequest = { onDropdownMenuExpandedChange(false) },
            ) {
              RefreshDropdownMenuItem(
                onClick = {
                  onDropdownMenuExpandedChange(false)
                  onRefresh()
                },
              )
              HorizontalDivider()
              LicensesDropdownMenuItem(
                onClick = {
                  onDropdownMenuExpandedChange(false)
                  onNavigateToLicenses()
                },
              )
            }
          }
        },
      )
    },
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState) { data ->
        SwipeToDismissBox(
          state = swipeToDismissBoxState,
          backgroundContent = {},
          modifier = Modifier
            .imePadding()
            .fillMaxWidth(),
        ) {
          Snackbar(
            snackbarData = data,
            modifier = Modifier
              .testTag("snackbar"),
          )
        }
      }
    },
  ) { padding ->
    val (containerPadding, contentPadding) = padding.toContainerAndContentPadding()

    PullToRefreshBox(
      isRefreshing = state.isRefreshing,
      onRefresh = onRefresh,
      modifier = Modifier.padding(containerPadding),
      state = pullToRefreshState,
      indicator = {
        Indicator(
          modifier = Modifier
            .align(Alignment.TopCenter)
            .testTag("pull-to-refresh-indicator"),
          isRefreshing = state.isRefreshing,
          state = pullToRefreshState,
        )
      },
    ) {
      CountriesLazyColumn(
        contentPadding = contentPadding,
        items = state.items,
        onNavigateToCountryDetails = onNavigateToCountryDetails,
      )
    }
  }
}

@Composable
private fun CountriesLazyColumn(
  items: List<CountryListItem>,
  onNavigateToCountryDetails: (CountryId) -> Unit,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(0.dp),
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize(),
    contentPadding = contentPadding,
  ) {
    items(
      items = items,
      key = { it.id.value },
    ) { item ->
      CountryListItem(
        officialName = item.officialName,
        emojiFlag = item.emojiFlag,
        modifier = Modifier
          .clickable {
            onNavigateToCountryDetails(item.id)
          },
      )
      HorizontalDivider()
    }
  }
}

@Composable
private fun LicensesDropdownMenuItem(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  DropdownMenuItem(
    text = {
      Text(text = stringResource(R.string.licenses_item_label))
    },
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Code,
        contentDescription = null,
      )
    },
    onClick = onClick,
    modifier = modifier,
  )
}

@Preview(showBackground = true)
@Composable
private fun CountriesScreenPreview() {
  RestCountriesTheme {
    CountriesScreen(
      state = CountriesScreenUiState(
        items = listOf(
          CountryListItem(
            id = CountryId("0"),
            officialName = "Albania",
            emojiFlag = "🇦🇱",
          ),
          CountryListItem(
            id = CountryId("1"),
            officialName = "Italy",
            emojiFlag = "🇮🇹",
          ),
          CountryListItem(
            id = CountryId("2"),
            officialName = "Germany",
            emojiFlag = "🇩🇪",
          ),
        ),
        isRefreshing = false,
        networkFailure = null,
        isDropdownMenuExpanded = false,
      ),
      onRefresh = {},
      onNavigateToCountryDetails = {},
      onNetworkFailureMessageDismissal = {},
      onDropdownMenuExpandedChange = {},
      onNavigateToLicenses = {},
    )
  }
}
