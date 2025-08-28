//
//  CountryListItem.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 13/08/25.
//

struct CountryListItem : Identifiable, Hashable {
  let id: CountryId
  let officialName: String
  let emojiFlag: String
}
