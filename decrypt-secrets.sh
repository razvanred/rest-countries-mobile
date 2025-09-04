#!/bin/bash

# Copyright 2021 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

decrypt() {
  PASSPHRASE=$1
  INPUT=$2
  OUTPUT=$3
  gpg --quiet --batch --yes --decrypt --passphrase="$PASSPHRASE" --output $OUTPUT $INPUT
}

if [[ ! -z "$REST_COUNTRIES_MOBILE_SECRETS_ENCRYPT_KEY" ]]; then
  # Decrypt Android app Release key
  decrypt ${REST_COUNTRIES_MOBILE_SECRETS_ENCRYPT_KEY} android-app/secrets/release.gpg android-app/secrets/release.jks
else
  echo "REST_COUNTRIES_MOBILE_SECRETS_ENCRYPT_KEY is empty"
fi
