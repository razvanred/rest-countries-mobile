//
//  Currency.swift
//  RestCountries
//
//  Created by Răzvan Roşu on 13/08/25.
//

import Foundation

public struct Currency: Hashable, Identifiable {
  public let id: CurrencyId
  public let name: String
  public let symbol: String
}
