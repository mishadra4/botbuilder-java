// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.epam.bot.connector.customizations;

import java.util.Map;

public interface ClaimsIdentity {
    boolean isAuthenticated();
    Map<String, String> claims();
    String getIssuer();
}
