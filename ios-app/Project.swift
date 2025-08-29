import Foundation
import ProjectDescription

private let kotlinModules = ["domain"]

let project = Project(
  name: "rest-countries",
  targets: [
    .target(
      name: "RestCountries",
      destinations: .iOS,
      product: .app,
      bundleId: "red.razvan.restcountries",
      infoPlist: .extendingDefault(
        with: [
          "UILaunchScreen": [
            "UIColorName": "",
            "UIImageName": "",
          ],
        ]
      ),
      sources: ["rest-countries/Sources/**"],
      resources: ["rest-countries/Resources/**"],
      scripts: kotlinModules.map { module in
        kotlinFrameworkConfig(module: module)
      },
      dependencies: [
        .external(name: "FactoryKit"),
      ],
      settings: .settings(
        base: SettingsDictionary()
          /// See the [Kotlin Multiplatform project integration guide](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-integrate-in-existing-app.html#connect-the-framework-to-your-ios-project).
          .frameworkSearchPaths(
            kotlinModules.map { module in
              kotlinFrameworkSearchPath(module: module)
            }
          )
      )
    ),
    .target(
      name: "RestCountriesTests",
      destinations: .iOS,
      product: .unitTests,
      bundleId: "red.razvan.restcountries.tests",
      infoPlist: .default,
      sources: ["rest-countries/Tests/**"],
      resources: [],
      dependencies: [
        .target(name: "RestCountries"),
        .external(name: "FactoryTesting"),
      ]
    ),
  ]
)

/// See the [Kotlin Multiplatform project integration guide](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-integrate-in-existing-app.html#connect-the-framework-to-your-ios-project).
private func kotlinFrameworkConfig(module: String) -> TargetScript {
  TargetScript.pre(
    script: "cd \"$SRCROOT/..\" && ./gradlew \(module):embedAndSignAppleFrameworkForXcode",
    name: "Compile \(module) Kotlin Framework"
  )
}

private func kotlinFrameworkSearchPath(module: String) -> String {
  "$(SRCROOT)/../\(module.replacingOccurrences(of: ":", with: "/"))/build/xcode-frameworks/$(CONFIGURATION)/$(SDK_NAME)"
}

private extension SettingsDictionary {
  // FRAMEWORK_SEARCH_PATHS
  func frameworkSearchPaths(_ value: [String]) -> SettingsDictionary {
    merging(["FRAMEWORK_SEARCH_PATHS": .array(value)])
  }

  func frameworkSearchPaths(_ value: String...) -> SettingsDictionary {
    frameworkSearchPaths(value)
  }
}
