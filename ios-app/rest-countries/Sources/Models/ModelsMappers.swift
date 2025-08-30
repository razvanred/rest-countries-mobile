//
//  ModelMappers.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 16/08/25.
//
import DomainKt

extension CountryId {
  func toModelKt() -> ModelsCountryId {
    ModelsCountryId(value: value)
  }
}
