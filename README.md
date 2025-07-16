# REST Countries

The __one and only__ Android application that displays the countries from the [REST Countries public API](https://restcountries.com/).

This is a small POC to demonstrate my abilities in the Android Development field.

## Features

 - Separated Gradle subprojects for each data layer, mainly:
   - `:data:db`
   - `:data:remote`
   - `:data:repository`
   - `:domain`
   - `:compose:app`
 - DI through [Koin](https://github.com/InsertKoinIO/koin)
 - Async calls and Reactive Flows through [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
 - Fetching from the REST API through [Ktor Client](https://github.com/ktorio/ktor)
 - Caching on a local database with [AndroidX Room](https://developer.android.com/jetpack/androidx/releases/room)
 - UI implemented using [Jetpack Compose](https://developer.android.com/compose)
 - Network errors clearly propagated through layers and displayed to the user
 - Screenshot testing with [Paparazzi](https://github.com/cashapp/paparazzi)
 - Unit tests using:
   - [JUnit4](https://github.com/junit-team/junit4)
   - [AssertK](https://github.com/willowtreeapps/assertk)
   - [Turbine](https://github.com/cashapp/turbine)
 - [Instrumented tests](https://developer.android.com/training/testing/instrumented-tests)
 - Shared build logic between subprojects through [convention plugins](https://docs.gradle.org/current/samples/sample_convention_plugins.html)

## Roadmap for `1.0`

 - [ ] Enhance `README.md` with device screenshots
 - [ ] Create more instrumented tests
 - [ ] Work on Compose performance optimizations
 - [ ] Migrate `:data:repository` layer to [Store](https://github.com/MobileNativeFoundation/Store)
 - [ ] Delete orphan columns from `language`, `continent`, `currency`, `capital` tables by creating a database trigger
 - [ ] Move common modules to [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/)
 - [ ] Create a proper CD pipeline

## Huge thanks to…

 - [Chris Banes](https://chrisbanes.me/), with his [tivi.app](https://github.com/chrisbanes/tivi): his project not only taught me during the years
    about the Android development, but also shaped me as a developer as a whole.
 - Although I never participated to this event, the [DroidKaigi](https://github.com/DroidKaigi) conference applications helped me
    during the early stages of my journey, and I am deeply grateful for the hard work the team is putting every year.
 - All the contributors of the mentioned libraries
