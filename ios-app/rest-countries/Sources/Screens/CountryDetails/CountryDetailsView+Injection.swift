//
//  CountryDetailsView+Injection.swift
//  RestCountries
//
//  Created by Răzvan Roşu on 24/08/25.
//

import DomainKt
import FactoryKit
import Foundation

public extension Container {
  var domainKtObserveDetailedCountryByIdOrNull: Factory<DomainKt.ObserveDetailedCountryByIdOrNull> {
    self {
      KoinDomainHelper.shared.observeDetailedCountryByIdOrNull
    }.singleton
  }

  var observeDetailedCountryByIdOrNil: Factory<ObserveDetailedCountryByIdOrNil> {
    self {
      DefaultObserveDetailedCountryByIdOrNil(observeDetailedCountryByIdOrNull: Container.shared.domainKtObserveDetailedCountryByIdOrNull())
    }.singleton
  }

  var domainKtRefreshDetailedCountryById: Factory<DomainKt.RefreshDetailedCountryById> {
    self {
      KoinDomainHelper.shared.refreshDetailedCountryById
    }.singleton
  }

  var refreshDetailedCountryById: Factory<RefreshDetailedCountryById> {
    self {
      DefaultRefreshDetailedCountryById(refreshDetailedCountryById: Container.shared.domainKtRefreshDetailedCountryById())
    }.singleton
  }
}
