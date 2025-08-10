import ProjectDescription

let project = Project(
    name: "rest-countries",
    targets: [
        .target(
            name: "RestCountries",
            destinations: .iOS,
            product: .app,
            bundleId: "red.razvan.preptime.restcountries",
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
            dependencies: []
        ),
        .target(
            name: "RestCountriesTests",
            destinations: .iOS,
            product: .unitTests,
            bundleId: "red.razvan.preptime.restcountries.tests",
            infoPlist: .default,
            sources: ["rest-countries/Tests/**"],
            resources: [],
            dependencies: [.target(name: "RestCountries")]
        ),
    ]
)
