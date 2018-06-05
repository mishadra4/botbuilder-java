package com.epam.bot.connector;

import com.epam.bot.connector.base.TestBase;
import com.epam.bot.connector.implementation.ConnectorClientImpl;
import com.epam.bot.schema.models.ChannelAccount;
import com.microsoft.rest.RestClient;

public class BotConnectorTestBase extends TestBase {
    protected ConnectorClientImpl connector;
    protected ChannelAccount bot;
    protected ChannelAccount user;

    public BotConnectorTestBase() {
        super(RunCondition.BOTH);
    }

    public BotConnectorTestBase(RunCondition runCondition) {
        super(runCondition);
    }

    @Override
    protected void initializeClients(RestClient restClient, String botId, String userId) {
        connector = new ConnectorClientImpl(restClient);
        bot = new ChannelAccount().withId(botId);
        user = new ChannelAccount().withId(userId);
    }

    @Override
    protected void cleanUpResources() {
    }
}