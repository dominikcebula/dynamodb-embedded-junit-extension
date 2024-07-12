package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.EmbeddedDynamoDbInitializer;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.InjectEmbeddedDynamoDb;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.WithEmbeddedDynamoDb;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class EmbeddedDynamoDbExtension implements BeforeEachCallback, AfterEachCallback {
    private DynamoDBProxyServer embeddedDynamoDb;

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        int embeddedDynamoDbPort = getEmbeddedDynamoDbPort(extensionContext);
        EmbeddedDynamoDBPortHolder.setPort(embeddedDynamoDbPort);

        embeddedDynamoDb = createServerFromCommandLineArgs(embeddedDynamoDbPort);
        embeddedDynamoDb.start();

        AmazonDynamoDB embeddedDynamoDBClient = createEmbeddedDynamoDBClient(embeddedDynamoDbPort);

        executeEmbeddedDynamoDbInitializers(extensionContext, embeddedDynamoDBClient);
        injectEmbeddedDynamoDbClient(extensionContext, embeddedDynamoDBClient);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        embeddedDynamoDb.stop();
        EmbeddedDynamoDBPortHolder.clearPort();
    }

    private int getEmbeddedDynamoDbPort(ExtensionContext extensionContext) {
        return extensionContext.getTestClass()
                .map(testClass -> testClass.getAnnotation(WithEmbeddedDynamoDb.class))
                .map(WithEmbeddedDynamoDb::port)
                .orElseThrow(() -> new IllegalStateException("Unable to extract Embedded DynamoDB port from test class"));
    }

    private DynamoDBProxyServer createServerFromCommandLineArgs(int port) throws ParseException {
        String[] args = {"-inMemory", "-port", String.valueOf(port)};

        return ServerRunner.createServerFromCommandLineArgs(args);
    }

    private AmazonDynamoDB createEmbeddedDynamoDBClient(int embeddedDynamoDbPort) {
        return new EmbeddedDynamoDBClientFactory()
                .create(embeddedDynamoDbPort);
    }

    private void executeEmbeddedDynamoDbInitializers(ExtensionContext extensionContext, AmazonDynamoDB embeddedDynamoDBClient) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        WithEmbeddedDynamoDb withEmbeddedDynamoDbAnnotation = getWithEmbeddedDynamoDbAnnotation(extensionContext);
        for (Class<? extends EmbeddedDynamoDbInitializer> embeddedDynamoDbInitializerClass : withEmbeddedDynamoDbAnnotation.embeddedDynamoDbInitializers()) {
            embeddedDynamoDbInitializerClass.getConstructor().newInstance().initialize(embeddedDynamoDBClient);
        }
    }

    private WithEmbeddedDynamoDb getWithEmbeddedDynamoDbAnnotation(ExtensionContext extensionContext) {
        return extensionContext.getTestClass()
                .map(aClass -> aClass.getAnnotation(WithEmbeddedDynamoDb.class))
                .orElseThrow(() -> new IllegalStateException("Unable to extract annotation " + WithEmbeddedDynamoDb.class.getSimpleName()));
    }

    private void injectEmbeddedDynamoDbClient(ExtensionContext extensionContext, AmazonDynamoDB embeddedDynamoDBClient) {
        extensionContext.getTestClass()
                .map(Class::getDeclaredFields)
                .stream()
                .flatMap(Arrays::stream)
                .filter(field -> field.isAnnotationPresent(InjectEmbeddedDynamoDb.class))
                .forEach(field -> injectEmbeddedDynamoDbClient(field, extensionContext, embeddedDynamoDBClient));
    }

    private void injectEmbeddedDynamoDbClient(Field field, ExtensionContext extensionContext, AmazonDynamoDB embeddedDynamoDBClient) {
        try {
            field.setAccessible(true);
            field.set(extensionContext.getRequiredTestInstance(), embeddedDynamoDBClient);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unable to inject embedded dynamodb client into a field " + field.getName(), e);
        }
    }
}
