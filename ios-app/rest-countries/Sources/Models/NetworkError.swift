//
//  NetworkError.swift
//  rest-countries
//
//  Created by Răzvan Roşu on 25/08/25.
//

enum NetworkError : Error {
  case withHttpStatusCode(code: Int)
  case undefined
}
