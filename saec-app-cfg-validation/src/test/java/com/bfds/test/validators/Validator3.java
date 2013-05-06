package com.bfds.test.validators;


import com.bfds.validation.Validator;
import com.bfds.validation.message.ValidationMessageContext;
import org.springframework.stereotype.Component;

@Component
public class Validator3 implements Validator {
    @Override
    public void validate(ValidationMessageContext messageContext) {
        messageContext.error("error in Validator3");
    }
}
