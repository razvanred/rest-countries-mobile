// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import red.razvan.restcountries.compose.app.internal.screens.NavDestinations
import red.razvan.restcountries.compose.app.internal.screens.NavDestinations.CountryDetailsScreen.Companion.toDestination
import red.razvan.restcountries.compose.app.internal.screens.countries.CountriesScreen
import red.razvan.restcountries.compose.app.internal.screens.details.CountryDetailsScreen
import red.razvan.restcountries.compose.design.RestCountriesTheme

@Composable
fun RestCountriesApp(
  modifier: Modifier = Modifier,
) {
  val navController = rememberNavController()

  RestCountriesTheme {
    NavHost(
      modifier = modifier,
      navController = navController,
      startDestination = NavDestinations.CountriesScreen,
    ) {
      composable<NavDestinations.CountriesScreen> {
        CountriesScreen(
          onNavigateToCountryDetails = { id ->
            navController
              .navigate(route = id.toDestination())
          },
        )
      }

      composable<NavDestinations.CountryDetailsScreen> { backStackEntry ->
        val destination = backStackEntry.toRoute<NavDestinations.CountryDetailsScreen>()

        CountryDetailsScreen(
          countryId = destination.countryId,
          onNavigateUp = {
            navController.popBackStack()
          },
        )
      }
    }
  }
}
