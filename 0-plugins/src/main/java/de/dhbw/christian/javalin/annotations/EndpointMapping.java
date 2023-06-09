package de.dhbw.christian.javalin.annotations;

import io.javalin.http.HandlerType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EndpointMapping {
    HandlerType handlerType();
    String endpoint();
}
