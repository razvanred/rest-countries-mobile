import DomainKt
import SwiftUI

@main
struct RestCountriesApp: App {
  init() {
    KoinDomainHelper.shared.startKoin()
  }

  var body: some Scene {
    WindowGroup {
      NavigationView {
        CountriesView()
      }
    }
  }
}
