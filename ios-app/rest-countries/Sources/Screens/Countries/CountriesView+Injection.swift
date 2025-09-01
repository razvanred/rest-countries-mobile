//
//  CountryListItemsComponent.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 16/08/25.
//
import DomainKt
import FactoryKit
import SwiftUI

public extension Container {
  var domainKtObserveCountryListItems: Factory<DomainKt.ObserveCountryListItems> {
    self {
      KoinDomainHelper.shared.observeCountryListItems
    }.singleton
  }

  var observeCountryListItems: Factory<ObserveCountryListItems> {
    self {
      DefaultObserveCountryListItems(
        observeCountryListItems: Container.shared.domainKtObserveCountryListItems(),
      )
    }.singleton
  }

  var domainKtRefreshCountryListItems: Factory<DomainKt.RefreshCountryListItems> {
    self {
      KoinDomainHelper.shared.refreshCountryListItems
    }.singleton
  }

  var refreshCountryListItems: Factory<RefreshCountryListItems> {
    self {
      DefaultRefreshCountryListItems(
        refreshCountryListItems: Container.shared.domainKtRefreshCountryListItems(),
      )
    }.singleton
  }
}
