# 🇵🇪 REST Countries

[![CI](https://github.com/razvanred/rest-countries-mobile/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/razvanred/rest-countries-mobile/actions/workflows/ci.yml)
[![ktlint](https://img.shields.io/badge/ktlint%20code--style-%E2%9D%A4-FF4081)](https://pinterest.github.io/ktlint/)

The _one and only_ Android application that displays the countries from the [REST Countries public API](https://restcountries.com/).

<div align="center" style="margin:auto">
   <picture>
      <source media="(prefers-color-scheme: dark)" srcset="docs/assets/pixel9a-dark-countries-screen.png" />
      <source media="(prefers-color-scheme: light)" srcset="docs/assets/pixel9a-light-countries-screen.png" />
      <img alt="Device screenshot of the Countries screen on a Pixel 9A" src="docs/assets/pixel9a-light-countries-screen.png" width="250px" hspace="20" vspace="250px" />
   </picture>
   <picture>
      <source media="(prefers-color-scheme: dark)" srcset="docs/assets/pixel9a-dark-details-screen.png" />
      <source media="(prefers-color-scheme: light)" srcset="docs/assets/pixel9a-light-details-screen.png" />
      <img alt='Device screenshot of the "Republic of Croatia" country detail screen on a Pixel 9A' src="docs/assets/pixel9a-light-details-screen.png" width="250px" hspace="20" vspace="250px" />
   </picture>
</div>

## 🌟 Tech stack

- 🛂 Separated Gradle subprojects for each data layer
- 💉 **Dependency Injection** through [Koin](https://github.com/InsertKoinIO/koin)
- 🌊 **Async calls and Reactive Flows** through [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- 🌍 **Remote fetching** from the REST API through [Ktor Client](https://github.com/ktorio/ktor)
- 📄 **Data serialization** through [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- 🍔 **Caching on a local database** with [AndroidX Room](https://developer.android.com/jetpack/androidx/releases/room)
- 📦 **Different data layer handling** through [Store](https://github.com/MobileNativeFoundation/Store)
- 🎨 **UI** implemented using [Jetpack Compose](https://developer.android.com/compose), with a separated Design System module
- ❗️ Network errors clearly propagated through layers and displayed to the user
- 📸 **Screenshot testing** with [Paparazzi](https://github.com/cashapp/paparazzi)
- ⚙️ **Unit tests** using:
  - [JUnit4](https://github.com/junit-team/junit4)
  - [AssertK](https://github.com/willowtreeapps/assertk)
  - [Turbine](https://github.com/cashapp/turbine)
- 🦾 [Instrumented tests](https://developer.android.com/training/testing/instrumented-tests)
- 🧩 **Shared build logic** between subprojects through [convention plugins](https://docs.gradle.org/current/samples/sample_convention_plugins.html)

## 🛣 Roadmap for `1.0`

- [x] Enhance `README.md` with device screenshots
- [x] Create more instrumented tests
- [ ] Work on Compose performance optimizations
- [x] Migrate `:data:repository` layer to [Store](https://github.com/MobileNativeFoundation/Store)
- [x] Delete orphan columns from `language`, `continent`, `currency`, `capital` tables by creating a database trigger
- [ ] Move common modules to [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/)
- [ ] Create iOS application & UI implementation with [SwiftUI](https://developer.apple.com/swiftui/)
- [ ] Validate external libraries licenses through [Licensee](https://github.com/cashapp/licensee)
- [ ] Create a proper CD pipeline

## 💟 Huge thanks to…

- [Chris Banes](https://chrisbanes.me/), with his [tivi.app](https://github.com/chrisbanes/tivi): his project not only taught me during the years
    about the Android development, but also shaped me as a developer in a general way
- Although I never participated to this event, the [DroidKaigi](https://github.com/DroidKaigi) conference applications helped me
    during the early stages of my journey, and I am deeply grateful for the hard work the team is putting every year
- [Now in Android](https://github.com/android/nowinandroid): A fully functional Android app built entirely with Kotlin and Jetpack Compose
- All the contributors of the mentioned libraries
- [REST Countries API contributors](https://gitlab.com/restcountries/restcountries)
