package com.bfds.validation;

import com.bfds.validation.message.ValidationMessageContext;

public interface Validator {

	void validate(final ValidationMessageContext messageContext);

    class Comparator implements java.util.Comparator<Validator> {

        public static final Comparator INSTANCE = new Comparator();

        private Comparator() {}

        public int compare(final Validator validator1, final Validator validator2) {
            boolean isAnnotationPresent = validator1.getClass().isAnnotationPresent(Order.class);
            final int value1 = isAnnotationPresent ? validator1.getClass().getAnnotation(Order.class).value() : -1;
            isAnnotationPresent = validator2.getClass().isAnnotationPresent(Order.class);
            final int value2 = isAnnotationPresent ? validator2.getClass().getAnnotation(Order.class).value() : -1;
            return (value1 > value2 ? -1 : (value1 == value2 ? 0 : 1));
        }

    }
}
