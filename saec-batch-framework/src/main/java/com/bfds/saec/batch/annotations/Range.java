package com.bfds.saec.batch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the position the annotated field occupies in a flat file record.
 * The value of the field when formatted as a string should be equal in length to he specified range.
 *
 * Example: -
 *
 * @Range("1-5")
 * private String str1;
 *
 * @Range("11-15")
 * private String str2;
 *
 * @Range("6-10")
 * private String str2;
 *
 * If str = "11111", str2 = "22222" and str3 = "33333"  then the file record  will look like "111113333322222"
 *
 * @see org.springframework.batch.item.file.transform.Range
 * @see Ranges
 */

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {
    String value();
    String property() default  "";
}
