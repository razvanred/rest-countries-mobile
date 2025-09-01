import Foundation
import ProjectDescription

private let kotlinAppModules = ["domain"]
private let kotlinAppTestsModules = ["test-resources:domain"]

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
      scripts: kotlinFrameworkConfigScripts(modules: kotlinAppModules),
      dependencies: [
        .external(name: "FactoryKit"),
      ],
      settings: .settings(
        base: SettingsDictionary()
          .kotlinFrameworks(modules: kotlinAppTestsModules)
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
      scripts: kotlinFrameworkConfigScripts(modules: kotlinAppTestsModules),
      dependencies: [
        .target(name: "RestCountries"),
        .external(name: "FactoryTesting"),
      ],
      settings: .settings(
        base: SettingsDictionary()
          .kotlinFrameworks(modules: kotlinAppTestsModules)
      )
    ),
  ]
)

private extension SettingsDictionary {
  /// See the [Kotlin Multiplatform project integration guide](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-integrate-in-existing-app.html#connect-the-framework-to-your-ios-project).
  func kotlinFrameworks(modules: [String]) -> SettingsDictionary {
    frameworkSearchPaths(
      modules.map { module in
        kotlinFrameworkSearchPath(module: module)
      }
    )
  }

  // FRAMEWORK_SEARCH_PATHS
  func frameworkSearchPaths(_ value: [String]) -> SettingsDictionary {
    merging(["FRAMEWORK_SEARCH_PATHS": .array(value)])
  }

  func frameworkSearchPaths(_ value: String...) -> SettingsDictionary {
    frameworkSearchPaths(value)
  }
}

/// See the [Kotlin Multiplatform project integration guide](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-integrate-in-existing-app.html#connect-the-framework-to-your-ios-project).
private func kotlinFrameworkConfigScripts(modules: [String]) -> [TargetScript] {
  modules.map { module in
    TargetScript.pre(
      script: "cd \"$SRCROOT/..\" && ./gradlew \(module):embedAndSignAppleFrameworkForXcode",
      name: "Compile \(module) Kotlin Framework"
    )
  }
}

private func kotlinFrameworkSearchPath(module: String) -> String {
  "$(SRCROOT)/../\(module.replacingOccurrences(of: ":", with: "/"))/build/xcode-frameworks/$(CONFIGURATION)/$(SDK_NAME)"
}
