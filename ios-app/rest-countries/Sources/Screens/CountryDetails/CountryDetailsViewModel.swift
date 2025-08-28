//
//  CountryDetailsViewModel.swift
//  RestCountries
//
//  Created by Răzvan Roşu on 16/08/25.
//
import SwiftUI
import DomainKt
import FactoryKit

final class CountryDetailsViewModel : ObservableObject {
  
  @Published
  private(set) var country: DetailedCountry? = nil
  
  @Published
  private(set) var networkError: NetworkError? = nil
  
  @Published
  private(set) var isRefreshing: Bool = false
  
  private let countryId: CountryId

  private let observeDetailedCountryByIdOrNil: ObserveDetailedCountryByIdOrNil = Container.shared.observeDetailedCountryByIdOrNil()
  private let refreshDetailedCountryById: RefreshDetailedCountryById = Container.shared.refreshDetailedCountryById()
  
  init(countryId: CountryId) {
    self.countryId = countryId
  }
  
  @MainActor
  func activate() async {
    for await country in observeDetailedCountryByIdOrNil(id: countryId) {
      self.country = country
    }
  }
  
  @MainActor
  func refresh() async {
    isRefreshing = true
    do {
      try await refreshDetailedCountryById(id: countryId)
    } catch let error {
      networkError = error
    }
    isRefreshing = false
  }
}
