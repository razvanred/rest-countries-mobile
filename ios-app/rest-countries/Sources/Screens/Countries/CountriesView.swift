//
//  CountriesView.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 16/08/25.
//
import FactoryKit
import SwiftUI

struct CountriesView: View {
  @State
  private var isInitialRefreshCompleted: Bool = false

  @StateObject
  private var viewModel = CountriesViewModel()

  var body: some View {
    ZStack {
      List(viewModel.countries) { country in
        NavigationLink(
          destination: CountryDetailsView(countryId: country.id)
        ) {
          CountryListItemView(country: country)
        }
      }

      if !isInitialRefreshCompleted, viewModel.countries.isEmpty {
        ProgressView()
      }
    }
    .if(isInitialRefreshCompleted) { view in
      view.refreshable {
        await viewModel.refresh()
      }
    }
    .navigationTitle("Countries")
    .task {
      await viewModel.activate()
    }
    .task {
      await viewModel.refresh()
      isInitialRefreshCompleted = true
    }
  }
}

private struct CountryListItemView: View {
  private let country: CountryListItem

  fileprivate init(country: CountryListItem) {
    self.country = country
  }

  fileprivate var body: some View {
    HStack {
      Text(country.emojiFlag)
      Text(country.officialName)
    }
  }
}

struct CountriesView_Previews: PreviewProvider {
  static var previews: some View {
    Text("Hello world!")
  }
}
