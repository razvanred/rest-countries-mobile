// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.android.compose.app.internal.screens.licenses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import red.razvan.restcountries.android.compose.app.R
import red.razvan.restcountries.android.compose.design.NavigateUpButton
import red.razvan.restcountries.android.compose.design.RestCountriesTheme
import red.razvan.restcountries.android.compose.design.toContainerAndContentPadding
import red.razvan.restcountries.android.domain.Artifact
import red.razvan.restcountries.android.domain.ArtifactsGroup
import red.razvan.restcountries.testresources.android.domain.SampleData as AndroidSampleData

@Composable
internal fun LicensesScreen(
  onNavigateUp: () -> Unit,
  onOpenUrl: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: LicensesViewModel = koinViewModel(),
) {
  val state by viewModel
    .state
    .collectAsStateWithLifecycle(initialValue = LicensesUiState.Empty)

  LicensesScreen(
    onNavigateUp = onNavigateUp,
    onOpenUrl = onOpenUrl,
    modifier = modifier,
    state = state,
  )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun LicensesScreen(
  state: LicensesUiState,
  onNavigateUp: () -> Unit,
  onOpenUrl: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val topAppBarScrollBehavior = TopAppBarDefaults
    .exitUntilCollapsedScrollBehavior()

  Scaffold(
    modifier = modifier
      .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
      .fillMaxSize(),
    topBar = {
      MediumFlexibleTopAppBar(
        title = {
          Text(text = stringResource(R.string.licenses_screen_title))
        },
        scrollBehavior = topAppBarScrollBehavior,
        navigationIcon = {
          NavigateUpButton(onClick = onNavigateUp)
        },
      )
    },
  ) { padding ->
    val (containerPadding, contentPadding) = padding.toContainerAndContentPadding()

    LicensesLazyColumn(
      contentPadding = contentPadding,
      items = state.artifacts,
      onItemClick = { artifact ->
        artifact.scm?.url?.let {
          onOpenUrl(it)
        }
      },
      modifier = Modifier
        .padding(containerPadding),
    )
  }
}

@Composable
private fun LicensesLazyColumn(
  items: List<ArtifactsGroup>,
  onItemClick: (Artifact) -> Unit,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(0.dp),
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize(),
    contentPadding = contentPadding,
  ) {
    items.forEach { group ->
      stickyHeader {
        ArtifactsGroupStickyHeader(title = group.id)
      }
      itemsIndexed(
        items = group.artifacts,
        key = { _, artifact ->
          "${artifact.groupId}.${artifact.artifactId}"
        },
      ) { i, artifact ->
        ArtifactItem(
          artifact = artifact,
          modifier = Modifier
            .clickable(
              onClick = {
                onItemClick(artifact)
              },
            )
            .fillMaxWidth(),
        )
        if (i < group.artifacts.lastIndex) {
          HorizontalDivider()
        }
      }
    }
  }
}

@Composable
private fun ArtifactsGroupStickyHeader(
  title: String,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier,
    tonalElevation = 2.dp,
    contentColor = MaterialTheme.colorScheme.primary,
  ) {
    Box(
      modifier = Modifier
        .padding(vertical = 8.dp, horizontal = 16.dp)
        .fillMaxWidth(),
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
      )
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ArtifactItem(
  artifact: Artifact,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(2.dp),
  ) {
    with(artifact) {
      Text(
        text = name ?: artifactId,
        style = MaterialTheme.typography.bodyLarge,
      )
      ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
        Column {
          Text(text = "$artifactId v$version")
          spdxLicenses?.forEach { license ->
            Text(text = license.name)
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun LicensesScreenPreview() {
  RestCountriesTheme {
    LicensesScreen(
      onNavigateUp = {},
      onOpenUrl = {},
      state = LicensesUiState(
        artifacts = AndroidSampleData.Artifacts.All,
      ),
    )
  }
}
