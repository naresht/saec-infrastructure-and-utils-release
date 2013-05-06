package com.bfds.saec.batch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated java.lang.Double field when converted to string must have its decimal point removed.
 * And when a String is set as the value, it must have a decimal point placed {@link com.bfds.saec.batch.annotations.AssumedDecimalNumberFormat#decimalPlaces()}
 * places from the right before being converted to java.lang.Double.
 *
 * Example: -
 *
 * @AssumedDecimalNumberFormat(decimalPlaces = 2)
 * private Double amount;
 *
 * If amount = 100.75d then it's string equivalent should be "10075".
 * Inversely "10075" should become 100.75d.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AssumedDecimalNumberFormat {
    int decimalPlaces();
}
