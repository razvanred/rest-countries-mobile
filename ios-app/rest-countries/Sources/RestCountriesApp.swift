import SwiftUI
import DomainKt

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
