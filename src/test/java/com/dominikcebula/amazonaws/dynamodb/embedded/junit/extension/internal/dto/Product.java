package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.math.BigDecimal;
import java.util.Objects;

@DynamoDBTable(tableName = "products")
public class Product {
    private Integer id;
    private String name;
    private String description;
    private String category;
    private String sku;
    private BigDecimal price;

    @SuppressWarnings("unused")
    public Product() {
    }

    public Product(Integer id, String name, String description, String category, String sku, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.sku = sku;
        this.price = price;
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

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @DynamoDBAttribute
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @DynamoDBAttribute
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(category, product.category) && Objects.equals(sku, product.sku) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, category, sku, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", sku='" + sku + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
