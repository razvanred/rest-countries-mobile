// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.core.utils

fun interface Mapper<in Source, out Destination> {
  fun map(source: Source): Destination
}
