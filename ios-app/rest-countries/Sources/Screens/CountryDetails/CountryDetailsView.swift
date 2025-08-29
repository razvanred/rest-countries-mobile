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
        Text(country.commonName)
      }
    }
    .navigationTitle("Country")
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
