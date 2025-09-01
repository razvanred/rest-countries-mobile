//
//  CountryListItem.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 13/08/25.
//

public struct CountryListItem: Identifiable, Hashable {
  public let id: CountryId
  public let officialName: String
  public let emojiFlag: String
}
