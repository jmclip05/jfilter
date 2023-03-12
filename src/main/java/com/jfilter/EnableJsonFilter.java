package com.jfilter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.lang.annotation.*;


/**
 * Enables json filter
 *
 * <p>For enabling FilterProvider, FilterAdvice and all filter mechanisms this annotation should be specified on one of application beans,
 * as example on application configuration bean.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@ComponentScans(value = @ComponentScan({"com.jfilter", "com.jfilter.components"}))
public @interface EnableJsonFilter {
}