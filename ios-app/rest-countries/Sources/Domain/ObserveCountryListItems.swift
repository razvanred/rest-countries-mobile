//
//  ObserveCountryListItems.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 13/08/25.
//
import DomainKt

protocol ObserveCountryListItems {
  func callAsFunction() -> any AsyncSequence<[CountryListItem], Never>
}

struct DefaultObserveCountryListItems: ObserveCountryListItems {
  private let observeCountryListItems: DomainKt.ObserveCountryListItems

  init(observeCountryListItems: DomainKt.ObserveCountryListItems) {
    self.observeCountryListItems = observeCountryListItems
  }

  func callAsFunction() -> any AsyncSequence<[CountryListItem], Never> {
    observeCountryListItems
      .invoke()
      .map { items in
        items.map { item in
          item.toModel()
        }
      }
  }
}
