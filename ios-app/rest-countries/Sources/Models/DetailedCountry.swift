//
//  DetailedCountry.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 13/08/25.
//

struct DetailedCountry : Identifiable, Hashable {
  let id: CountryId
  let officialName: String
  let commonName: String
  let emojiFlag: String
  let flag: Flag
  let currencies: [Currency]
  let capital: [String]
  let continents: [String]
  let languages: [Language]
  
  var isOfficialNameDifferentFromCommon: Bool {
    !(officialName.caseInsensitiveCompare(commonName) == .orderedSame)
  }
  
  struct Flag : Hashable {
    let png: String
    let svg: String
    let contentDescription: String
  }
}
