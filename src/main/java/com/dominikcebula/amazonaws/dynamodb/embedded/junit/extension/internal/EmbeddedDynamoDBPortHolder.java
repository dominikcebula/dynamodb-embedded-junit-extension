package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal;

public class EmbeddedDynamoDBPortHolder {
    private static int port;

    public static int getPort() {
        return port;
    }

    public static void setPort(int newPort) {
        port = newPort;
    }
}
