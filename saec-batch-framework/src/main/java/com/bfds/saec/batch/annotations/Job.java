package com.bfds.saec.batch.annotations;


import com.bfds.saec.batch.job.JobBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is not yet implemented.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Job {
    Class<? extends JobBuilder> processor();
    String xmlRootTagName() default "";
    String xmlRootTagNamespace() default "";
}
