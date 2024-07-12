package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.examples;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.*;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.InjectEmbeddedDynamoDbClient;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.WithEmbeddedDynamoDb;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// Shows basic usage of @WithEmbeddedDynamoDb annotation
// and @InjectEmbeddedDynamoDbClient annotation to have Embedded Dynamo DB Client injected.
@WithEmbeddedDynamoDb
public class Example1 {
    @InjectEmbeddedDynamoDbClient
    private AmazonDynamoDB embeddedDynamoDBClient;

    @Test
    void shouldSaveProductData() {
        // given
        createdProductsTable();
        Product product = new Product(1, "Smartphone");
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(embeddedDynamoDBClient);

        // when
        dynamoDBMapper.save(product);

        // then
        Product loadedProduct = dynamoDBMapper.load(Product.class, 1);

        assertThat(loadedProduct.getId()).isEqualTo(1);
        assertThat(loadedProduct.getName()).isEqualTo("Smartphone");
    }

    private void createdProductsTable() {
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName("products")
                .withKeySchema(
                        new KeySchemaElement("id", KeyType.HASH)
                )
                .withAttributeDefinitions(
                        new AttributeDefinition("id", ScalarAttributeType.N)
                )
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        embeddedDynamoDBClient.createTable(createTableRequest);
    }

    @DynamoDBTable(tableName = "products")
    public static class Product {
        private Integer id;
        private String name;

        @SuppressWarnings("unused")
        public Product() {
        }

        public Product(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        @DynamoDBHashKey
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @DynamoDBAttribute
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
