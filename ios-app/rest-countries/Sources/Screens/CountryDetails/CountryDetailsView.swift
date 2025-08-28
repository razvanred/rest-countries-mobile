//
//  CountryDetailsView.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 16/08/25.
//
import SwiftUI

struct CountryDetailsView : View {
  
  @StateObject
  private var viewModel: CountryDetailsViewModel
  
  @State
  private var isInitialRefreshCompleted = false
  
  init(countryId: CountryId) {
    _viewModel = StateObject(wrappedValue: CountryDetailsViewModel(countryId: countryId))
  }
  
  var body: some View {
    ZStack {
      List {
        if let country = viewModel.country {
          Text(country.commonName)
        }
      }
      
      if !isInitialRefreshCompleted {
        ProgressView()
      }
    }
    .navigationTitle("Country")
    .navigationSubtitle(viewModel.isRefreshing ? "Refreshing…" : "")
    .navigationBarTitleDisplayMode(.inline)
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
