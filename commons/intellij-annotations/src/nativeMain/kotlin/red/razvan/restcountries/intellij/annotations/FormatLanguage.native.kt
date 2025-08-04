// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.intellij.annotations

@Retention(AnnotationRetention.BINARY)
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.FIELD,
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.LOCAL_VARIABLE,
  AnnotationTarget.ANNOTATION_CLASS,
)
actual annotation class FormatLanguage(
  actual val value: String,
  actual val prefix: String,
  actual val suffix: String,
)
