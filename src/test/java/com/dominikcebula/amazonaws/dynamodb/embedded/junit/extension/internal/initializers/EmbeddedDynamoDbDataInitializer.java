package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.initializers;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.EmbeddedDynamoDbInitializer;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.sample.data.SampleData;

public class EmbeddedDynamoDbDataInitializer implements EmbeddedDynamoDbInitializer {
    @Override
    public void initialize(AmazonDynamoDB embeddedAmazonDynamoDB) {
        SampleData.initialize(embeddedAmazonDynamoDB);
    }
}
