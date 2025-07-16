// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.compose.app.internal

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import red.razvan.restcountries.compose.design.RestCountriesTheme

@RunWith(AndroidJUnit4::class)
class CountryDetailsScreenTests : KoinTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun helloWorld() {
    composeTestRule.setContent {
      RestCountriesTheme {
        Text(text = "hello world!")
      }
    }

    composeTestRule
      .onNodeWithText("hello world!")
      .assertIsDisplayed()
  }
}
