package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.WithEmbeddedDynamoDb;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.dto.Product;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.sample.data.SampleData;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.sample.data.SampleTables;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class EmbeddedDynamoDbExtensionTest {
    @Nested
    @WithEmbeddedDynamoDb
    class DefaultPortTests extends TestCases {
        @Override
        AmazonDynamoDB getAmazonDynamoDB() {
            return new EmbeddedDynamoDBClientFactory().create(8092);
        }
    }

    @Nested
    @WithEmbeddedDynamoDb(port = DefinedPortTests.DEFINED_PORT)
    class DefinedPortTests extends TestCases {
        private static final int DEFINED_PORT = 8295;

        @Override
        AmazonDynamoDB getAmazonDynamoDB() {
            return new EmbeddedDynamoDBClientFactory().create(DEFINED_PORT);
        }
    }

    @Nested
    @WithEmbeddedDynamoDb
    class DefaultPortWithPortHolderTests extends TestCases {
        @Override
        AmazonDynamoDB getAmazonDynamoDB() {
            return new EmbeddedDynamoDBClientFactory().create();
        }
    }

    @Nested
    @WithEmbeddedDynamoDb(port = 8762)
    class DefinedPortWithPortHolderTests extends TestCases {
        @Override
        AmazonDynamoDB getAmazonDynamoDB() {
            return new EmbeddedDynamoDBClientFactory().create();
        }
    }

    private abstract static class TestCases {
        abstract AmazonDynamoDB getAmazonDynamoDB();

        @Test
        void shouldConnectToEmbeddedDb() {
            AmazonDynamoDB amazonDynamoDB = getAmazonDynamoDB();

            assertNotNull(amazonDynamoDB.listTables());
        }

        @Test
        void shouldCreateTablesOnEmbeddedDb() {
            AmazonDynamoDB amazonDynamoDB = getAmazonDynamoDB();

            SampleTables.initialize(amazonDynamoDB);

            assertThat(amazonDynamoDB.listTables().getTableNames())
                    .containsOnly(
                            "products",
                            "customers",
                            "carts"
                    );
        }

        @Test
        void shouldCreateTablesAndInsertData() {
            AmazonDynamoDB amazonDynamoDB = getAmazonDynamoDB();
            DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);

            SampleTables.initialize(amazonDynamoDB);
            SampleData.initialize(amazonDynamoDB);

            List<Product> products = mapper.scan(Product.class, new DynamoDBScanExpression());

            assertThat(products)
                    .containsOnly(
                            new Product(1, "Smartphone",
                                    "Latest model with 6.5-inch display and 128GB storage", "electronics",
                                    "electronics-smartphone-001", new BigDecimal("699.99")
                            ),
                            new Product(2, "Running Shoes",
                                    "Comfortable and lightweight running shoes", "sports",
                                    "sports-runningshoes-002", new BigDecimal("89.99")),
                            new Product(3, "Dining Table",
                                    "Wooden dining table with a seating capacity of 6", "furniture",
                                    "furniture-diningtable-003", new BigDecimal("299.99"))
                    );
        }
    }
}
