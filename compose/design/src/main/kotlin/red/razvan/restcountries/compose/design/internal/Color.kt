// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.design.internal

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val Purple80 = Color(0xFFD0BCFF)
private val PurpleGrey80 = Color(0xFFCCC2DC)
private val Pink80 = Color(0xFFEFB8C8)

private val Purple40 = Color(0xFF6650a4)
private val PurpleGrey40 = Color(0xFF625b71)
private val Pink40 = Color(0xFF7D5260)

internal val DarkColorScheme = darkColorScheme(
  primary = Purple80,
  secondary = PurpleGrey80,
  tertiary = Pink80,
)

internal val LightColorScheme = lightColorScheme(
  primary = Purple40,
  secondary = PurpleGrey40,
  tertiary = Pink40,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
     */
)
