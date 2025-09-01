//
//  ObserveDetailedCountryByIdOrNil.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 16/08/25.
//
import DomainKt

public protocol ObserveDetailedCountryByIdOrNil {
  func callAsFunction(id: CountryId) -> any AsyncSequence<DetailedCountry?, Never>
}

struct DefaultObserveDetailedCountryByIdOrNil: ObserveDetailedCountryByIdOrNil {
  private let observeDetailedCountryByIdOrNull: DomainKt.ObserveDetailedCountryByIdOrNull

  public init(observeDetailedCountryByIdOrNull: DomainKt.ObserveDetailedCountryByIdOrNull) {
    self.observeDetailedCountryByIdOrNull = observeDetailedCountryByIdOrNull
  }

  func callAsFunction(id: CountryId) -> any AsyncSequence<DetailedCountry?, Never> {
    observeDetailedCountryByIdOrNull
      .invoke(id: id.toModelKt())
      .map { $0?.toModel() }
  }
}
