//
//  CountryDetailsView.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 16/08/25.
//
import SwiftUI

struct CountryDetailsView: View {
  @StateObject
  private var viewModel: CountryDetailsViewModel

  @State
  private var isInitialRefreshCompleted = false

  init(countryId: CountryId) {
    _viewModel = StateObject(wrappedValue: CountryDetailsViewModel(countryId: countryId))
  }

  var body: some View {
    List {
      if let country = viewModel.country {
        LoadedContentView(country: country)
      }
    }
    .navigationTitle(String(localized: "CountryDetailsViewNavigationTitle"))
    .navigationBarTitleDisplayMode(.inline)
    .toolbar {
      if !isInitialRefreshCompleted {
        ToolbarItem(placement: .topBarTrailing) {
          ProgressView()
        }
      }
    }
    .if(isInitialRefreshCompleted) { view in
      view.refreshable {
        await viewModel.refresh()
      }
    }
    .task {
      await viewModel.activate()
    }
    .task {
      await viewModel.refresh()
      isInitialRefreshCompleted = true
    }
  }
}

private struct LoadedContentView: View {
  private let country: DetailedCountry

  init(country: DetailedCountry) {
    self.country = country
  }

  var body: some View {
    Section {
      HStack(alignment: .center) {
        AsyncImage(url: URL(string: country.flag.png)) { result in
          result.image?
            .resizable()
            .scaledToFit()
        }
        .frame(width: 100)

        VStack(alignment: .leading) {
          Text(country.officialName)
            .font(.title2)
          if country.isOfficialNameDifferentFromCommon {
            Text(String(localized: "CountryAlsoKnownAsTitle \(country.commonName)"))
              .font(.subheadline)
          }
        }
        .padding(.horizontal)
      }
      .padding()
    }

    if !country.continents.isEmpty {
      Section(
        country.continents.count == 1
          ? String(localized: "ContinentSectionTitle")
          : String(localized: "ContinentsSectionTitle")
      ) {
        ForEach(country.continents, id: \.self) { continent in
          Text(continent)
        }
      }
    }

    if !country.capital.isEmpty {
      Section(
        country.capital.count == 1
          ? String(localized: "CapitalSectionTitle")
          : String(localized: "CapitalsSectionTitle")
      ) {
        ForEach(country.capital, id: \.self) { capital in
          Text(capital)
        }
      }
    }

    if !country.languages.isEmpty {
      Section(
        country.languages.count == 1
          ? String(localized: "LanguageSectionTitle")
          : String(localized: "LanguagesSectionTitle")
      ) {
        ForEach(country.languages) { language in
          Text(language.name)
        }
      }
    }

    if !country.currencies.isEmpty {
      Section(
        country.currencies.count == 1
          ? String(localized: "CurrencySectionTitle")
          : String(localized: "CurrenciesSectionTitle")
      ) {
        ForEach(country.currencies) { currency in
          Text(String(localized: "CurrencyPropertyValue \(currency.name) \(currency.symbol)"))
        }
      }
    }

    Section {
      ListItemDetailView(title: String(localized: "EmojiFlagPropertyTitle"), value: country.emojiFlag)
    }
  }
}

private struct ListItemDetailView: View {
  private let title: String
  private let value: String

  init(title: String, value: String) {
    self.title = title
    self.value = value
  }

  var body: some View {
    HStack {
      Text(title)
      Spacer()
      Text(value)
        .font(.callout)
    }
  }
}
