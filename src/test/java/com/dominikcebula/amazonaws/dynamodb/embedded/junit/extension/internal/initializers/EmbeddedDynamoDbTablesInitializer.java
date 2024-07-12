package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.initializers;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.EmbeddedDynamoDbInitializer;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.sample.data.SampleTables;

public class EmbeddedDynamoDbTablesInitializer implements EmbeddedDynamoDbInitializer {
    @Override
    public void initialize(AmazonDynamoDB embeddedAmazonDynamoDB) {
        SampleTables.initialize(embeddedAmazonDynamoDB);
    }
}
