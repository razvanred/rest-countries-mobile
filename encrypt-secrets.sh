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

encrypt() {
  PASSPHRASE=$1
  INPUT=$2
  OUTPUT=$3
  gpg --batch --yes --passphrase="$PASSPHRASE" --cipher-algo AES256 --symmetric --output $OUTPUT $INPUT
}

if [[ ! -z "$REST_COUNTRIES_MOBILE_SECRETS_ENCRYPT_KEY" ]]; then
  # Encrypt Android app Release key
  encrypt ${REST_COUNTRIES_MOBILE_SECRETS_ENCRYPT_KEY} android/app/secrets/release.jks android/app/secrets/release.gpg
else
  echo "REST_COUNTRIES_MOBILE_SECRETS_ENCRYPT_KEY is empty"
fi
