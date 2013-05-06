package com.bfds.saec.batch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation has two uses
 * 1. To specify a {@link Range} on an inherited/super class field
 * 2. To specify a filler. A filler is used when certain positions in the output flat file record have to be either left blank or filled with a
 * specific character.
 *
 *  Example :-
 *
 * @Ranges({@Range(property = "fill(1)", value = "1"),
 * @Range(property = "fill('0', 5)", value = "3-7")})
 * public class SomeClass  {...}
 *
 * In the above code snippet we have 2 fillers
 *
 * fill(1) - A single character filler at position 1(indicated by value). The fill character defaults to ' '.
 * fill(0, 5) - A 5 character filler starting at position 3 and ending at position 7 with '0' as the fill character.
 *
 * Note: Do not explicitly specify ' ' as the fill character - fill(' ', 5). It will not work. Use fill(5) instead.
 *
 * @see Range
 * @see org.springframework.batch.item.file.transform.Range
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Ranges {
    Range [] value ();
}
