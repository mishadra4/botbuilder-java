package com.microsoft.bot.connector;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.aad.adal4j.AuthenticationException;
import com.microsoft.bot.connector.customizations.CredentialProvider;
import com.microsoft.bot.connector.customizations.JwtTokenValidation;
import com.microsoft.bot.connector.customizations.MicrosoftAppCredentials;
import com.microsoft.bot.connector.implementation.ConnectorClientImpl;
import com.microsoft.bot.connector.sample.App;
import com.microsoft.bot.schema.models.Activity;
import com.microsoft.bot.schema.models.ActivityTypes;
import com.microsoft.bot.schema.models.ResourceResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleMessageHandler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger( SimpleMessageHandler.class.getName() );
    private ObjectMapper objectMapper;
    private CredentialProvider credentialProvider;
    private MicrosoftAppCredentials credentials;

    public SimpleMessageHandler(CredentialProvider credentialProvider) {
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules();
        this.credentialProvider = credentialProvider;
        this.credentials = new MicrosoftAppCredentials("0fae9c81-5bea-4439-beab-5bf39bf8b01d", "mkTW5!{;kqukiCNMDS6349_");
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        if (httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
            Activity activity = getActivity(httpExchange);
            String authHeader = httpExchange.getRequestHeaders().getFirst("Authorization");
            try {
                JwtTokenValidation.assertValidActivity(activity, authHeader, credentialProvider);

                // send ack to user activity
                httpExchange.sendResponseHeaders(202, 0);
                httpExchange.getResponseBody().close();

                if (activity.type().equals(ActivityTypes.MESSAGE)) {
                    // reply activity with the same text
                    ConnectorClientImpl connector = new ConnectorClientImpl(activity.serviceUrl(), this.credentials);
                    ResourceResponse response = connector.conversations().sendToConversation(activity.conversation().id(),
                            new Activity()
                                    .withType(ActivityTypes.MESSAGE)
                                    .withText("Epam echo: " + activity.text())
                                    .withRecipient(activity.from())
                                    .withFrom(activity.recipient())
                    );
                }
            } catch (AuthenticationException ex) {
                httpExchange.sendResponseHeaders(401, 0);
                httpExchange.getResponseBody().close();
                LOGGER.log(Level.WARNING, "Auth failed!", ex);
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Execution failed", ex);
            }
        }
    }

    private String getRequestBody(HttpExchange httpExchange) throws IOException {
        StringBuilder buffer = new StringBuilder();
        InputStream stream = httpExchange.getRequestBody();
        int rByte;
        while ((rByte = stream.read()) != -1) {
            buffer.append((char)rByte);
        }
        stream.close();
        if (buffer.length() > 0) {
            return buffer.toString();
        }
        return "";
    }

    private Activity getActivity(HttpExchange httpExchange) {
        try {
            String body = getRequestBody(httpExchange);
            LOGGER.log(Level.INFO, body);
            return objectMapper.readValue(body, Activity.class);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Failed to get activity", ex);
            return null;
        }

    }
}