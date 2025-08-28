//
//  CountryListItemsComponent.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 16/08/25.
//
import DomainKt
import SwiftUI
import FactoryKit

extension Container {
 
  var observeCountryListItems: Factory<ObserveCountryListItems> {
    self {
      DefaultObserveCountryListItems(
        observeCountryListItems: KoinDomainHelper.shared.observeCountryListItems,
      )
    }.singleton
  }
  
  var refreshCountryListItems: Factory<RefreshCountryListItems> {
    self {
      DefaultRefreshCountryListItems(refreshCountryListItems: KoinDomainHelper.shared.refreshCountryListItems)
    }
  }
}
