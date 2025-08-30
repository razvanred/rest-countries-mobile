// Copyright 2025 Răzvan Roșu
// SPDX-License-Identifier: Apache-2.0

package red.razvan.restcountries.intellij.annotations

/**
 * Copied from KotlinX Serialization's [FormatLanguage.kt](https://github.com/Kotlin/kotlinx.serialization/blob/master/formats/json/commonMain/src/kotlinx/serialization/json/internal/FormatLanguage.kt#L6)
 *
 * See [KTIJ-16340](https://youtrack.jetbrains.com/issue/KTIJ-16340/Support-Language-injection-annotation-on-Kotlin-Common-Multiplatform)
 */
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
expect annotation class FormatLanguage(
  val value: String,
  // default parameters are not used due to https://youtrack.jetbrains.com/issue/KT-25946/
  val prefix: String = "",
  val suffix: String = "",
)
