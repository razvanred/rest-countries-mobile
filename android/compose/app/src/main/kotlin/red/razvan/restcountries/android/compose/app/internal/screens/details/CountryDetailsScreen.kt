// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ProvideTextStyle
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import red.razvan.restcountries.android.compose.app.R
import red.razvan.restcountries.android.compose.app.internal.mappers.LocalNetworkFailureToMessageMapper
import red.razvan.restcountries.android.compose.design.ConnectableCardProperties
import red.razvan.restcountries.android.compose.design.ConnectedCardGroup
import red.razvan.restcountries.android.compose.design.MoreOptionsVertButton
import red.razvan.restcountries.android.compose.design.NavigateUpButton
import red.razvan.restcountries.android.compose.design.RefreshDropdownMenuItem
import red.razvan.restcountries.android.compose.design.toContainerAndContentPadding
import red.razvan.restcountries.data.models.CountryId
import red.razvan.restcountries.data.models.Currency
import red.razvan.restcountries.data.models.DetailedCountry
import red.razvan.restcountries.data.models.Language

@Composable
internal fun CountryDetailsScreen(
  countryId: CountryId,
  onNavigateUp: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val viewModel =
    koinViewModel<CountryDetailsScreenViewModel> { parametersOf(countryId) }

  CountryDetailsScreen(
    viewModel = viewModel,
    onNavigateUp = onNavigateUp,
    modifier = modifier,
  )
}

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
  val pullToRefreshState = rememberPullToRefreshState()

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
      modifier = Modifier
        .padding(containerPadding)
        .fillMaxSize(),
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
      if (state.country != null) {
        CountryDetailsContent(
          country = state.country,
          contentPadding = contentPadding,
          modifier = Modifier
            .testTag("content"),
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
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(contentPadding),
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

      Column(
        verticalArrangement = Arrangement
          .spacedBy(4.dp),
        modifier = Modifier
          .padding(top = 16.dp),
      ) {
        if (country.continents.isNotEmpty()) {
          ContinentsCard(
            continents = country.continents,
          )
        }

        if (country.capital.isNotEmpty()) {
          CapitalCard(
            capitals = country.capital,
          )
        }

        if (country.languages.isNotEmpty()) {
          LanguagesCard(
            languages = country.languages,
          )
        }

        if (country.currencies.isNotEmpty()) {
          CurrenciesCard(
            currencies = country.currencies,
          )
        }

        OtherDetailsCard(
          emojiFlag = country.emojiFlag,
        )
      }
    }
  }
}

@Composable
private fun ContinentsCard(
  continents: List<String>,
  modifier: Modifier = Modifier,
) {
  ConnectedCardGroup(
    title = {
      Text(
        text = pluralStringResource(
          R.plurals.country_details_screen_continents_card_title,
          continents.size,
        ),
      )
    },
    modifier = modifier,
    cards = continents.map { continent ->
      ConnectableCardProperties(
        content = @Composable {
          Text(text = continent)
        },
      )
    },
  )
}

@Composable
private fun CapitalCard(
  capitals: List<String>,
  modifier: Modifier = Modifier,
) {
  ConnectedCardGroup(
    title = {
      Text(
        text = pluralStringResource(
          R.plurals.country_details_screen_capitals_card_title,
          capitals.size,
        ),
      )
    },
    modifier = modifier,
    cards = capitals.map { capital ->
      ConnectableCardProperties(
        content = @Composable {
          Text(text = capital)
        },
      )
    },
  )
}

@Composable
private fun LanguagesCard(
  languages: List<Language>,
  modifier: Modifier = Modifier,
) {
  ConnectedCardGroup(
    title = {
      Text(
        text = pluralStringResource(
          R.plurals.country_details_screen_languages_cards_title,
          languages.size,
        ),
      )
    },
    modifier = modifier,
    cards = languages.map { language ->
      ConnectableCardProperties(
        content = @Composable {
          Text(text = language.name)
        },
      )
    },
  )
}

@Composable
private fun CurrenciesCard(
  currencies: List<Currency>,
  modifier: Modifier = Modifier,
) {
  ConnectedCardGroup(
    title = {
      Text(
        text = pluralStringResource(
          R.plurals.country_details_screen_currencies_card_title,
          currencies.size,
        ),
      )
    },
    modifier = modifier,
    cards = currencies.map { currency ->
      ConnectableCardProperties(
        content = @Composable {
          Text(
            text = stringResource(
              R.string.country_details_screen_currency_property_value,
              currency.name,
              currency.symbol,
            ),
          )
        },
      )
    },
  )
}

@Composable
private fun OtherDetailsCard(
  emojiFlag: String,
  modifier: Modifier = Modifier,
) {
  ConnectedCardGroup(
    title = {
      Text(
        text = stringResource(R.string.country_details_screen_other_details_card_title),
      )
    },
    modifier = modifier,
    cards = listOf(
      ConnectableCardProperties(
        content = {
          EmojiFlagItem(emojiFlag = emojiFlag)
        },
      ),
    ),
  )
}

@Composable
private fun EmojiFlagItem(
  emojiFlag: String,
  modifier: Modifier = Modifier,
) {
  ListItemDetail(
    modifier = modifier,
    title = {
      Text(text = stringResource(R.string.country_details_screen_emoji_flag_property_title))
    },
    value = {
      Text(text = emojiFlag)
    },
  )
}

@Composable
private fun ListItemDetail(
  title: @Composable () -> Unit,
  value: @Composable () -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement
      .spacedBy(4.dp),
  ) {
    Box(
      modifier = Modifier.weight(1f),
      contentAlignment = Alignment.CenterStart,
    ) {
      ProvideTextStyle(MaterialTheme.typography.bodyLarge) {
        title()
      }
    }
    Box(
      modifier = Modifier.weight(1f),
      contentAlignment = Alignment.CenterEnd,
    ) {
      ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
        value()
      }
    }
  }
}
