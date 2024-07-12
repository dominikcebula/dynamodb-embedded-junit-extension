package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

public interface EmbeddedDynamoDBInitializer {
    void initialize(AmazonDynamoDB embeddedAmazonDynamoDB);
}
