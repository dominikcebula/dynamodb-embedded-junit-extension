package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.sample.data;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;

public class SampleTables {
    public static void initialize(AmazonDynamoDB embeddedAmazonDynamoDB) {
        initializeTable(embeddedAmazonDynamoDB, "products");
        initializeTable(embeddedAmazonDynamoDB, "customers");
        initializeTable(embeddedAmazonDynamoDB, "carts");
    }

    private static void initializeTable(AmazonDynamoDB embeddedAmazonDynamoDB, String tableName) {
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(
                        new KeySchemaElement("id", KeyType.HASH)
                )
                .withAttributeDefinitions(
                        new AttributeDefinition("id", ScalarAttributeType.N)
                )
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        embeddedAmazonDynamoDB.createTable(createTableRequest);
    }
}
