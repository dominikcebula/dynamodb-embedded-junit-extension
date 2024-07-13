package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal;

public class EmbeddedDynamoDBPortHolder {
    private static final Integer NO_PORT = null;

    private static Integer port = NO_PORT;

    public static Integer getPort() {
        if (port == NO_PORT) {
            throw new IllegalStateException("Embedded Dynamo DB port was not set. " +
                    "Port should be set automatically by extension code, prior being queried.");
        }

        return port;
    }

    public static void setPort(Integer newPort) {
        port = newPort;
    }

    public static void clearPort() {
        setPort(NO_PORT);
    }
}
