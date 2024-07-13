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
  <version>1.1</version>
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

* [Basic usage example of `@WithEmbeddedDynamoDB` annotation with `@InjectEmbeddedDynamoDBClient` annotation](src%2Ftest%2Fjava%2Fcom%2Fdominikcebula%2Famazonaws%2Fdynamodb%2Fembedded%2Fjunit%2Fextension%2Finternal%2Fexamples%2FExample1.java)
* [Embedded Dynamo DB Initializer Usage to initialize table definitions](src%2Ftest%2Fjava%2Fcom%2Fdominikcebula%2Famazonaws%2Fdynamodb%2Fembedded%2Fjunit%2Fextension%2Finternal%2Fexamples%2FExample2.java)
* [Embedded Dynamo DB Initializers Usage to initialize table definitions and sample data](src%2Ftest%2Fjava%2Fcom%2Fdominikcebula%2Famazonaws%2Fdynamodb%2Fembedded%2Fjunit%2Fextension%2Finternal%2Fexamples%2FExample3.java)
* [Defined port usage](src%2Ftest%2Fjava%2Fcom%2Fdominikcebula%2Famazonaws%2Fdynamodb%2Fembedded%2Fjunit%2Fextension%2Finternal%2Fexamples%2FExample4.java)
* [Embedded Dynamo DB Client creation programmatically using `EmbeddedDynamoDBClientFactory`](src%2Ftest%2Fjava%2Fcom%2Fdominikcebula%2Famazonaws%2Fdynamodb%2Fembedded%2Fjunit%2Fextension%2Finternal%2Fexamples%2FExample5.java)
* [Embedded Dynamo DB Client creation programmatically using `EmbeddedDynamoDBClientFactory` by specifying port manually](src%2Ftest%2Fjava%2Fcom%2Fdominikcebula%2Famazonaws%2Fdynamodb%2Fembedded%2Fjunit%2Fextension%2Finternal%2Fexamples%2FExample6.java)

## Author

Dominik Cebula

* https://dominikcebula.com/
* https://blog.dominikcebula.com/
* https://www.udemy.com/user/dominik-cebula/
