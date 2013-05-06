package com.bfds.saec.batch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated java.util.Date field when converted to String must be formatted as {@link com.bfds.saec.batch.annotations.DateFormat#value()}
 * And the String being set as the value must be formatted as {@link com.bfds.saec.batch.annotations.DateFormat#value()}.
 *
 * Example: -
 *
 * @DateFormat("YYMMDD")
 * private java.lang.Date date;
 *
 * If date = new Date(112, 0, 1) then it's string equivalent should be "120101".
 * Inversely "120101" should become Date(112, 0, 1).
 *
 * @see com.bfds.saec.batch.format.propertyeditors.CustomDateEditor for supported data formats.
 *
 * Use {@link org.springframework.beans.propertyeditors.CustomDateEditor} for standard date formats.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
    String value();
}
