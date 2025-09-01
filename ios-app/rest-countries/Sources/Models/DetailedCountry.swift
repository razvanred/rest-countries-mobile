//
//  DetailedCountry.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 13/08/25.
//

public struct DetailedCountry: Identifiable, Hashable {
  public let id: CountryId
  public let officialName: String
  public let commonName: String
  public let emojiFlag: String
  public let flag: Flag
  public let currencies: [Currency]
  public let capital: [String]
  public let continents: [String]
  public let languages: [Language]

  public var isOfficialNameDifferentFromCommon: Bool {
    !(officialName.caseInsensitiveCompare(commonName) == .orderedSame)
  }

  public struct Flag: Hashable {
    public let png: String
    public let svg: String
    public let contentDescription: String
  }
}
