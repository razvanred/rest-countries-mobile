// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import red.razvan.restcountries.compose.app.R
import red.razvan.restcountries.compose.app.internal.mappers.LocalNetworkFailureToMessageMapper
import red.razvan.restcountries.compose.design.MoreOptionsVertButton
import red.razvan.restcountries.compose.design.NavigateUpButton
import red.razvan.restcountries.compose.design.PropertiesCard
import red.razvan.restcountries.compose.design.Property
import red.razvan.restcountries.compose.design.RefreshDropdownMenuItem
import red.razvan.restcountries.compose.design.toContainerAndContentPadding
import red.razvan.restcountries.data.models.Currency
import red.razvan.restcountries.data.models.DetailedCountry

@Composable
internal fun CountryDetailsScreen(
  viewModel: CountryDetailsScreenViewModel,
  onNavigateUp: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val state by viewModel
    .state
    .collectAsStateWithLifecycle(CountryDetailsUiState.Empty)

  CountryDetailsScreen(
    state = state,
    onRefresh = viewModel::refresh,
    onNavigateUp = onNavigateUp,
    onNetworkFailureMessageDismissal = viewModel::clearNetworkFailure,
    onDropdownMenuExpandedChange = viewModel::setDropdownMenuExpanded,
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CountryDetailsScreen(
  state: CountryDetailsUiState,
  onDropdownMenuExpandedChange: (Boolean) -> Unit,
  onRefresh: () -> Unit,
  onNetworkFailureMessageDismissal: () -> Unit,
  onNavigateUp: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val topAppBarScrollBehavior = TopAppBarDefaults
    .pinnedScrollBehavior()

  val snackbarHostState = remember { SnackbarHostState() }

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
      TopAppBar(
        title = {
          if (state.country != null) {
            Text(
              text = stringResource(R.string.country_details_screen_title),
            )
          }
        },
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
            }
          }
        },
        navigationIcon = {
          NavigateUpButton(
            onClick = onNavigateUp,
          )
        },
        scrollBehavior = topAppBarScrollBehavior,
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
          Snackbar(snackbarData = data)
        }
      }
    },
  ) { padding ->
    val (containerPadding, contentPadding) = padding.toContainerAndContentPadding()

    PullToRefreshBox(
      isRefreshing = state.isRefreshing,
      onRefresh = onRefresh,
      modifier = Modifier
        .padding(containerPadding)
        .fillMaxSize(),
    ) {
      if (state.country != null) {
        CountryDetailsContent(
          country = state.country,
          contentPadding = contentPadding,
        )
      }
    }
  }
}

@Composable
private fun CountryDetailsContent(
  country: DetailedCountry,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(0.dp),
) {
  Column(
    modifier = modifier
      .padding(contentPadding)
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
  ) {
    AsyncImage(
      model = country.flag.svg,
      contentDescription = country.flag.contentDescription,
      modifier = Modifier
        .height(220.dp)
        .fillMaxWidth(),
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 20.dp),
    ) {
      Text(
        text = country.officialName,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
          .fillMaxWidth(),
      )

      if (country.isOfficialNameDifferentFromCommon) {
        Text(
          text = stringResource(
            R.string.country_details_country_common_name_label,
            country.commonName,
          ),
          style = MaterialTheme.typography.titleSmall,
          modifier = Modifier
            .padding(vertical = 4.dp),
        )
      }

      CountryDetailsCard(
        emojiFlag = country.emojiFlag,
        capital = country.capital,
        continents = country.continents,
        languages = country.languages.map { it.name },
        currencies = country.currencies,
        modifier = Modifier.padding(top = 16.dp),
      )
    }
  }
}

@Composable
private fun CountryDetailsCard(
  emojiFlag: String,
  capital: List<String>,
  continents: List<String>,
  languages: List<String>,
  currencies: List<Currency>,
  modifier: Modifier = Modifier,
) {
  PropertiesCard(
    properties = {
      if (continents.isNotEmpty()) {
        Property(
          title = {
            Text(
              text = pluralStringResource(
                R.plurals.country_details_screen_continents_property_title,
                capital.size,
              ),
            )
          },
          value = {
            Text(
              text = continents.joinToString(separator = ", "),
            )
          },
        )
      }

      if (capital.isNotEmpty()) {
        Property(
          title = {
            Text(
              text = pluralStringResource(
                R.plurals.country_details_screen_capital_property_title,
                capital.size,
              ),
            )
          },
          value = {
            Text(
              text = capital.joinToString(separator = ", "),
            )
          },
        )
      }

      if (languages.isNotEmpty()) {
        Property(
          title = {
            Text(
              text = pluralStringResource(
                R.plurals.country_details_screen_languages_property_title,
                languages.size,
              ),
            )
          },
          value = {
            Text(
              text = languages.joinToString(separator = ", "),
            )
          },
        )
      }

      if (currencies.isNotEmpty()) {
        Property(
          title = {
            Text(
              text = pluralStringResource(
                R.plurals.country_details_screen_currencies_property_title,
                currencies.size,
              ),
            )
          },
          value = {
            @Suppress("SimplifiableCallChain")
            Text(
              text = currencies
                .map { currency ->
                  stringResource(
                    R.string.country_details_currencies_property_value,
                    currency.name,
                    currency.symbol,
                  )
                }
                .joinToString(separator = ", "),
            )
          },
        )
      }

      Property(
        title = {
          Text(
            text = stringResource(R.string.country_details_screen_emoji_flag_property_title),
          )
        },
        value = {
          Text(
            text = emojiFlag,
          )
        },
      )
    },
    modifier = modifier,
  )
}
