package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.sample.data;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Map;

public class SampleData {
    public static void initialize(AmazonDynamoDB embeddedAmazonDynamoDB) {
        embeddedAmazonDynamoDB.putItem(
                "products",
                Map.of(
                        "id", new AttributeValue().withN("1"),
                        "name", new AttributeValue().withS("Smartphone"),
                        "description", new AttributeValue().withS("Latest model with 6.5-inch display and 128GB storage"),
                        "category", new AttributeValue().withS("electronics"),
                        "sku", new AttributeValue().withS("electronics-smartphone-001"),
                        "price", new AttributeValue().withN("699.99")
                )
        );

        embeddedAmazonDynamoDB.putItem(
                "products",
                Map.of(
                        "id", new AttributeValue().withN("2"),
                        "name", new AttributeValue().withS("Running Shoes"),
                        "description", new AttributeValue().withS("Comfortable and lightweight running shoes"),
                        "category", new AttributeValue().withS("sports"),
                        "sku", new AttributeValue().withS("sports-runningshoes-002"),
                        "price", new AttributeValue().withN("89.99")
                )
        );

        embeddedAmazonDynamoDB.putItem(
                "products",
                Map.of(
                        "id", new AttributeValue().withN("3"),
                        "name", new AttributeValue().withS("Dining Table"),
                        "description", new AttributeValue().withS("Wooden dining table with a seating capacity of 6"),
                        "category", new AttributeValue().withS("furniture"),
                        "sku", new AttributeValue().withS("furniture-diningtable-003"),
                        "price", new AttributeValue().withN("299.99")
                )
        );
    }
}
