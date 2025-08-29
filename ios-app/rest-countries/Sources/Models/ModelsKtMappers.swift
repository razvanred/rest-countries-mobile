//
//  ModelsKtMappers.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 13/08/25.
//
import DomainKt

extension ModelsCurrencyId {
  func toModel() -> CurrencyId {
    CurrencyId(value: value)
  }
}

extension ModelsCurrency {
  func toModel() -> Currency {
    Currency(id: id.toModel(), name: name, symbol: symbol)
  }
}

extension ModelsLanguageId {
  func toModel() -> LanguageId {
    LanguageId(value: value)
  }
}

extension ModelsLanguage {
  func toModel() -> Language {
    Language(id: id.toModel(), name: name)
  }
}

extension ModelsCountryId {
  func toModel() -> CountryId {
    CountryId(value: value)
  }
}

extension ModelsCountryListItem {
  func toModel() -> CountryListItem {
    CountryListItem(id: id.toModel(), officialName: officialName, emojiFlag: emojiFlag)
  }
}

extension ModelsDetailedCountry {
  func toModel() -> DetailedCountry {
    DetailedCountry(
      id: id.toModel(),
      officialName: officialName,
      commonName: commonName,
      emojiFlag: emojiFlag,
      flag: DetailedCountry.Flag(
        png: flag.png,
        svg: flag.svg,
        contentDescription: flag.contentDescription,
      ),
      currencies: currencies.map { currency in
        currency.toModel()
      },
      capital: capital,
      continents: continents,
      languages: languages.map { language in
        language.toModel()
      },
    )
  }
}

extension ModelsNetworkFailure {
  func toModel() -> NetworkError {
    switch self {
    case let failure as ModelsNetworkFailureWithHttpStatusCode:
      .withHttpStatusCode(code: Int(failure.code))
    case is ModelsNetworkFailureUndefined:
      .undefined
    default:
      fatalError("The NetworkFailure \(self) is not recognized")
    }
  }
}
