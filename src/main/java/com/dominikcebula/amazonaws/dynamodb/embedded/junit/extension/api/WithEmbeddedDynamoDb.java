package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api;

import com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.internal.EmbeddedDynamoDbExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ExtendWith(EmbeddedDynamoDbExtension.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithEmbeddedDynamoDb {
    int port() default 8092;

    Class<? extends EmbeddedDynamoDbInitializer>[] embeddedDynamoDbInitializers() default {};
}
