package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.examples;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.EmbeddedDynamoDBInitializer;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.InjectEmbeddedDynamoDBClient;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.WithEmbeddedDynamoDB;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.examples.Example3.ProductsDataInitializer;
import static com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.examples.Example3.ProductsTableInitializer;
import static org.assertj.core.api.Assertions.assertThat;

// Shows usage of multiple embeddedDynamoDBInitializers together with @WithEmbeddedDynamoDB
// and @InjectEmbeddedDynamoDBClient annotations. Implemented ProductsTableInitializer initializes DynamoDB Table,
// and ProductsDataInitializer inserts sample data.
@WithEmbeddedDynamoDB(embeddedDynamoDBInitializers = {ProductsTableInitializer.class, ProductsDataInitializer.class})
class Example3 {
    @InjectEmbeddedDynamoDBClient
    private AmazonDynamoDB embeddedDynamoDBClient;

    @Test
    void shouldLoadProductsData() {
        // given
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(embeddedDynamoDBClient);

        // when
        List<Product> products = dynamoDBMapper.scan(Product.class, new DynamoDBScanExpression());

        // then
        assertThat(products).containsOnly(
                new Product(1, "Smartphone"),
                new Product(2, "Running Shoes"),
                new Product(3, "Dining Table")
        );
    }

    public static class ProductsTableInitializer implements EmbeddedDynamoDBInitializer {
        @Override
        public void initialize(AmazonDynamoDB embeddedAmazonDynamoDB) {
            CreateTableRequest createTableRequest = new CreateTableRequest()
                    .withTableName("products")
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

    public static class ProductsDataInitializer implements EmbeddedDynamoDBInitializer {
        @Override
        public void initialize(AmazonDynamoDB embeddedAmazonDynamoDB) {
            DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(embeddedAmazonDynamoDB);
            dynamoDBMapper.batchSave(List.of(
                    new Product(1, "Smartphone"),
                    new Product(2, "Running Shoes"),
                    new Product(3, "Dining Table")
            ));
        }
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return Objects.equals(id, product.id) && Objects.equals(name, product.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }
}
