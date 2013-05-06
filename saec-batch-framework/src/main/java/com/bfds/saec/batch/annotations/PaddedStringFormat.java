package com.bfds.saec.batch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated String field must be padded before returned to the client program.
 * The amount of padding is String.length() - {@link com.bfds.saec.batch.annotations.PaddedStringFormat#padding()}.
 *
 * And when a String is set as the value, it must have its padding removed.
 *
 * The behavior is not specified if  String.length() - {@link com.bfds.saec.batch.annotations.PaddedStringFormat#padding()} is < 0.
 * The client program must ensure the String value being set is not larger than {@link com.bfds.saec.batch.annotations.PaddedStringFormat#padding()}
 *
 * The behavior is not specified when used on non-string fields.
 *
 * @see com.bfds.saec.batch.format.propertyeditors.CompositeEditor for using this on non-string fields.
 *
 * Example: -
 *
 * @PaddedStringFormat(padding = Padding.LPAD, paddingChar = '0', size = 10)
 * private String str;
 *
 * If str = "100" then the returned value must be  be "0000000100".
 * Inversely "0000000100" should become "100".
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PaddedStringFormat {
    Padding padding();
    char paddingChar();
    int size();
    boolean truncate() default true;
}
