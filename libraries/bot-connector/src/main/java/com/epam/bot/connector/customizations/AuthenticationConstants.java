// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.epam.bot.connector.customizations;

import java.util.ArrayList;
import java.util.List;

public final class AuthenticationConstants {
    public static final String ToChannelFromBotLoginUrl = "https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token";
    public static final String ToChannelFromBotOAuthScope = "https://api.botframework.com/.default";
    public static final String ToBotFromChannelTokenIssuer  = "https://api.botframework.com";
    public static final String ToBotFromChannelOpenIdMetadataUrl = "https://login.botframework.com/v1/.well-known/openidconfiguration";
    public static final String ToBotFromEmulatorOpenIdMetadataUrl = "https://login.microsoftonline.com/common/v2.0/.well-known/openid-configuration";
    public static final List<String> AllowedSigningAlgorithms = new ArrayList<>();
    public static final String AuthorizedParty = "azp";
    public static final String AudienceClaim = "aud";
    public static final String ServiceUrlClaim = "serviceurl";
    public static final String VersionClaim = "ver";
    public static final String AppIdClaim = "appid";

    static {
        AllowedSigningAlgorithms.add("RS256");
        AllowedSigningAlgorithms.add("RS384");
        AllowedSigningAlgorithms.add("RS512");
    }
}
