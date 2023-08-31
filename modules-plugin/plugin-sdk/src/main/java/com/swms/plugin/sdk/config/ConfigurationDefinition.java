package com.swms.plugin.sdk.config;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * only supports one plugin one this annotation now
 */
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
@Documented
public @interface ConfigurationDefinition {

}
