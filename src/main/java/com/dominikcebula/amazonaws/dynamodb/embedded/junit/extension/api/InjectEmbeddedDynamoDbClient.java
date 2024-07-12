package com.dominikcebula.amazonaws.dynamodb.embedded.junit.extension.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectEmbeddedDynamoDbClient {
}
