package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class EmbeddedDynamoDBClientFactory {
    public AmazonDynamoDB create() {
        return create(EmbeddedDynamoDBPortHolder.getPort());
    }

    public AmazonDynamoDB create(int embeddedDynamoDBPort) {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        getEndpointConfiguration(embeddedDynamoDBPort)
                )
                .build();
    }

    private EndpointConfiguration getEndpointConfiguration(int embeddedDynamoDbPort) {
        return new EndpointConfiguration(getEmbeddedDynamoDbEndpoint(embeddedDynamoDbPort), "local");
    }

    private String getEmbeddedDynamoDbEndpoint(int embeddedDynamoDbPort) {
        return "http://localhost:" + embeddedDynamoDbPort;
    }
}
