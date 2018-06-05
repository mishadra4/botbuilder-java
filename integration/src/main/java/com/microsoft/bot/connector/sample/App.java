// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.connector.sample;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.aad.adal4j.AuthenticationException;
import com.microsoft.bot.connector.customizations.CredentialProvider;
import com.microsoft.bot.connector.customizations.CredentialProviderImpl;
import com.microsoft.bot.connector.customizations.JwtTokenValidation;
import com.microsoft.bot.connector.customizations.MicrosoftAppCredentials;
import com.microsoft.bot.connector.implementation.ConnectorClientImpl;
import com.microsoft.bot.schema.models.Activity;
import com.microsoft.bot.schema.models.ActivityTypes;
import com.microsoft.bot.schema.models.ResourceResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static String appId = "0fae9c81-5bea-4439-beab-5bf39bf8b01d";       // <-- app id -->
    private static String appPassword = "mkTW5!{;kqukiCNMDS6349_"; // <-- app password -->
    private static int port = 3978;

    public static void main( String[] args ) throws IOException {
        CredentialProvider credentialProvider = new CredentialProviderImpl(appId, appPassword);
        //BotChannelHttpsServer server = new BotChannelHttpsServer(credentialProvider, args[0]);
//        BotChannelHttpsServer server = new BotChannelHttpsServer(credentialProvider, "B://mycert.keystore");
        BotChannelHttpsServer server = new BotChannelHttpsServer(credentialProvider,  "integration/src/main" +
                "/java/com/microsoft/bot/connector/sample/mycert.keystore");
        server.Start(port);
    }
}
