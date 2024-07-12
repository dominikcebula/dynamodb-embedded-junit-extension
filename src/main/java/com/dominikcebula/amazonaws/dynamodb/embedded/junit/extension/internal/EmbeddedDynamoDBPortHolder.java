package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal;

public class EmbeddedDynamoDBPortHolder {
    private static final int NO_PORT = -1;

    private static int port = NO_PORT;

    public static int getPort() {
        return port;
    }

    public static void setPort(int newPort) {
        port = newPort;
    }

    public static void clearPort() {
        setPort(NO_PORT);
    }
}
