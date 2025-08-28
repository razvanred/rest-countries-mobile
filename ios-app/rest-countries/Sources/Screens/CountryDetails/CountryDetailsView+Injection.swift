//
//  CountryDetailsView+Injection.swift
//  RestCountries
//
//  Created by Răzvan Roşu on 24/08/25.
//

import Foundation
import FactoryKit
import DomainKt

extension Container {
  var observeDetailedCountryByIdOrNil: Factory<ObserveDetailedCountryByIdOrNil> {
    self {
      DefaultObserveDetailedCountryByIdOrNil(observeDetailedCountryByIdOrNull: KoinDomainHelper.shared.observeDetailedCountryByIdOrNull)
    }
  }

  var refreshDetailedCountryById: Factory<RefreshDetailedCountryById> {
    self {
      DefaultRefreshDetailedCountryById(refreshDetailedCountryById: KoinDomainHelper.shared.refreshDetailedCountryById)
    }
  }
}
