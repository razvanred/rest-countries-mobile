//
//  Currency.swift
//  RestCountries
//
//  Created by Răzvan Roşu on 13/08/25.
//

import Foundation

struct Currency: Hashable, Identifiable {
  let id: CurrencyId
  let name: String
  let symbol: String
}
