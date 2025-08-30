//
//  CountriesViewModel.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 16/08/25.
//
import DomainKt
import FactoryKit
import SwiftUI

final class CountriesViewModel: ObservableObject {
  @Published
  private(set) var countries: [CountryListItem] = []

  @Published
  private(set) var networkError: NetworkError? = nil

  @Published
  private(set) var isRefreshing: Bool = false

  private let observeCountryListItems: ObserveCountryListItems = Container.shared.observeCountryListItems()
  private let refreshCountryListItems: RefreshCountryListItems = Container.shared.refreshCountryListItems()

  @MainActor
  func activate() async {
    for await countries in observeCountryListItems() {
      self.countries = countries
    }
  }

  @MainActor
  func refresh() async {
    isRefreshing = true
    do {
      try await refreshCountryListItems()
    } catch {
      networkError = error
    }
    isRefreshing = false
  }
}
