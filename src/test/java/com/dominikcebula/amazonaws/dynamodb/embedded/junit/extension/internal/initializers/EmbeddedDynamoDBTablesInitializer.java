package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.initializers;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.EmbeddedDynamoDBInitializer;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.sample.data.SampleTables;

public class EmbeddedDynamoDBTablesInitializer implements EmbeddedDynamoDBInitializer {
    @Override
    public void initialize(AmazonDynamoDB embeddedAmazonDynamoDB) {
        SampleTables.initialize(embeddedAmazonDynamoDB);
    }
}
