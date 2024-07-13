package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.EmbeddedDynamoDBInitializer;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.InjectEmbeddedDynamoDBClient;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api.WithEmbeddedDynamoDB;
import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.utils.EmbeddedDynamoDBClientFactory;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Field;
import java.util.Arrays;

public class EmbeddedDynamoDBExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {
    private DynamoDBProxyServer embeddedDynamoDB;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        int embeddedDynamoDBPort = getEmbeddedDynamoDBPort(extensionContext);
        EmbeddedDynamoDBPortHolder.setPort(embeddedDynamoDBPort);
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        EmbeddedDynamoDBPortHolder.clearPort();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        int embeddedDynamoDBPort = getEmbeddedDynamoDBPort(extensionContext);

        embeddedDynamoDB = createServerFromCommandLineArgs(embeddedDynamoDBPort);
        embeddedDynamoDB.start();

        AmazonDynamoDB embeddedDynamoDBClient = createEmbeddedDynamoDBClient(embeddedDynamoDBPort);

        executeEmbeddedDynamoDBInitializers(extensionContext, embeddedDynamoDBClient);
        injectEmbeddedDynamoDBClient(extensionContext, embeddedDynamoDBClient);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        embeddedDynamoDB.stop();
    }

    private int getEmbeddedDynamoDBPort(ExtensionContext extensionContext) {
        return extensionContext.getTestClass()
                .map(testClass -> testClass.getAnnotation(WithEmbeddedDynamoDB.class))
                .map(WithEmbeddedDynamoDB::port)
                .orElseThrow(() -> new IllegalStateException("Unable to extract Embedded DynamoDB port from test class"));
    }

    private DynamoDBProxyServer createServerFromCommandLineArgs(int port) throws ParseException {
        String[] args = {"-inMemory", "-port", String.valueOf(port)};

        return ServerRunner.createServerFromCommandLineArgs(args);
    }

    private AmazonDynamoDB createEmbeddedDynamoDBClient(int embeddedDynamoDBPort) {
        return new EmbeddedDynamoDBClientFactory()
                .create(embeddedDynamoDBPort);
    }

    private void executeEmbeddedDynamoDBInitializers(ExtensionContext extensionContext, AmazonDynamoDB embeddedDynamoDBClient) {
        WithEmbeddedDynamoDB withEmbeddedDynamoDBAnnotation = getWithEmbeddedDynamoDBAnnotation(extensionContext);
        for (Class<? extends EmbeddedDynamoDBInitializer> embeddedDynamoDBInitializerClass : withEmbeddedDynamoDBAnnotation.embeddedDynamoDBInitializers()) {
            try {
                embeddedDynamoDBInitializerClass.getConstructor().newInstance().initialize(embeddedDynamoDBClient);
            } catch (Exception e) {
                throw new IllegalStateException("Unable to create an instance of a class " + embeddedDynamoDBInitializerClass.getSimpleName() +
                        ". Make sure that class has non-argument constructor and class is public.");
            }
        }
    }

    private WithEmbeddedDynamoDB getWithEmbeddedDynamoDBAnnotation(ExtensionContext extensionContext) {
        return extensionContext.getTestClass()
                .map(aClass -> aClass.getAnnotation(WithEmbeddedDynamoDB.class))
                .orElseThrow(() -> new IllegalStateException("Unable to extract annotation " + WithEmbeddedDynamoDB.class.getSimpleName()));
    }

    private void injectEmbeddedDynamoDBClient(ExtensionContext extensionContext, AmazonDynamoDB embeddedDynamoDBClient) {
        extensionContext.getTestClass()
                .map(Class::getDeclaredFields)
                .stream()
                .flatMap(Arrays::stream)
                .filter(field -> field.isAnnotationPresent(InjectEmbeddedDynamoDBClient.class))
                .forEach(field -> injectEmbeddedDynamoDBClient(field, extensionContext, embeddedDynamoDBClient));
    }

    private void injectEmbeddedDynamoDBClient(Field field, ExtensionContext extensionContext, AmazonDynamoDB embeddedDynamoDBClient) {
        try {
            field.setAccessible(true);
            field.set(extensionContext.getRequiredTestInstance(), embeddedDynamoDBClient);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unable to inject embedded dynamodb client into a field " + field.getName(), e);
        }
    }
}
