[![CI Pipeline](https://github.com/dominikcebula/dynamodb-embedded-junit-extension/actions/workflows/ci.yml/badge.svg)](https://github.com/dominikcebula/dynamodb-embedded-junit-extension/actions/workflows/ci.yml)

# JUnit 5 Embedded DynamoDB Extension

## Intro

This repository contains a JUnit 5 extension allowing you to run an Embedded DynamoDB during unit tests.

## Features

* Start / stop Embedded DynamoDB before / after every test using `@WithEmbeddedDynamoDB`
* Inject Embedded DynamoDB Client into test field using `@InjectEmbeddedDynamoDBClient`
* Create Embedded DynamoDB Client programmatically using `EmbeddedDynamoDBClientFactory`
* Define custom port for Embedded DynamoDB using `port` attribute of `@WithEmbeddedDynamoDB`
* Use custom `EmbeddedDynamoDBInitializer` to initialize Embedded DynamoDB, for example:
    * Create table definitions
    * Insert sample data

## Usage

Add Maven dependency to the following artifact:

```xml

<dependency>
    <groupId>com.dominikcebula.amazonaws.dynamodb.embedded</groupId>
    <artifactId>dynamodb-embedded-junit-extension</artifactId>
    <version>1.0</version>
    <scope>test</scope>
</dependency>
```

Implement Unit Test with Embedded DynamoDB using `@WithEmbeddedDynamoDB`:

```java

@WithEmbeddedDynamoDB
class SomeTest {
    @InjectEmbeddedDynamoDBClient
    private AmazonDynamoDB embeddedDynamoDBClient;

    @Test
    void shouldDoSomethingWhenSomeActionExecuted() {
        // given
        // ...

        // when
        // ...

        // then
        // ...
    }
}
```

## Examples

TBD

## Author

Dominik Cebula

* https://dominikcebula.com/
* https://blog.dominikcebula.com/
* https://www.udemy.com/user/dominik-cebula/
