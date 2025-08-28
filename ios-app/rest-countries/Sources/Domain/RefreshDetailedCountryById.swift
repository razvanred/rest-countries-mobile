//
//  RefreshDetailedCountryById.swift
//  RestCountries
//
//  Created by Răzvan Roşu on 27/08/25.
//

import Foundation
import DomainKt

protocol RefreshDetailedCountryById {
  func callAsFunction(id: CountryId) async throws(NetworkError)
}

struct DefaultRefreshDetailedCountryById : RefreshDetailedCountryById {
  
  private let refreshDetailedCountryById: DomainKt.RefreshDetailedCountryById
  
  init(refreshDetailedCountryById: DomainKt.RefreshDetailedCountryById) {
    self.refreshDetailedCountryById = refreshDetailedCountryById
  }
  
  func callAsFunction(id: CountryId) async throws(NetworkError) {
    for await status in refreshDetailedCountryById.invoke(id: id.toModelKt()) {
      if status is InvokeStatusFailure<ModelsNetworkFailure> {
        throw (status as! InvokeStatusFailure<ModelsNetworkFailure>).error!.toModel()
      }
      
      if status is InvokeStatusSuccessful<KotlinUnit> {
        return
      }
    }
  }
}
