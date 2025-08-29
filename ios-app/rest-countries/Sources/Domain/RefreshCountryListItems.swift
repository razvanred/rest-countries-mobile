//
//  RefreshCountryListItems.swift
//  RestCountries
//
//  Created by Răzvan Roşu on 25/08/25.
//

import DomainKt
import Foundation

protocol RefreshCountryListItems {
  func callAsFunction() async throws(NetworkError)
}

struct DefaultRefreshCountryListItems: RefreshCountryListItems {
  private let refreshCountryListItems: DomainKt.RefreshCountryListItems

  init(refreshCountryListItems: DomainKt.RefreshCountryListItems) {
    self.refreshCountryListItems = refreshCountryListItems
  }

  func callAsFunction() async throws(NetworkError) {
    for await status in refreshCountryListItems.invoke() {
      if status is InvokeStatusFailure<ModelsNetworkFailure> {
        throw (status as! InvokeStatusFailure<ModelsNetworkFailure>).error!.toModel()
      }

      if status is InvokeStatusSuccessful<KotlinUnit> {
        return
      }
    }
  }
}
