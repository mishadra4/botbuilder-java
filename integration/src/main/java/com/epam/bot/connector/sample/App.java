// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.epam.bot.connector.sample;

import com.epam.bot.connector.customizations.CredentialProvider;
import com.epam.bot.connector.customizations.CredentialProviderImpl;

import java.io.IOException;

public class App {
    private static String appId = "0fae9c81-5bea-4439-beab-5bf39bf8b01d";       // <-- app id -->
    private static String appPassword = "mkTW5!{;kqukiCNMDS6349_"; // <-- app password -->
    private static int port = 3978;

    public static void main( String[] args ) throws IOException {
        CredentialProvider credentialProvider = new CredentialProviderImpl(appId, appPassword);
        //BotChannelHttpsServer server = new BotChannelHttpsServer(credentialProvider, args[0]);
//        BotChannelHttpsServer server = new BotChannelHttpsServer(credentialProvider, "B://mycert.keystore");
        BotChannelHttpsServer server = new BotChannelHttpsServer(credentialProvider,  "integration/src/main" +
                "/java/com/epam/bot/connector/sample/mycert.keystore");
        server.Start(port);
    }
}
