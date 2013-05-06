package com.bfds.validation.execution;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import java.util.ArrayList;
import java.util.List;

import com.bfds.validation.message.*;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bfds.validation.Validator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/applicationContext-test.xml")
@Transactional
public class ValidatorExecutorImplTest {

	@Autowired
	ValidatorExecutor validatorExecutor;

	@After
	public void after() {
		(new MessageUtil()).deleteAllMessages();
	}

	@Test
	public void execute() {
		List<Validator> validators = new ArrayList<Validator>();
		validators.add(new Validator() {

			@Override
			public void validate(ValidationMessageContext messageContext) {
				messageContext.error("error 1");
			}
		});
		validators.add(new Validator() {

			@Override
			public void validate(ValidationMessageContext messageContext) {
				messageContext.error("error 2");
			}
		});

		validatorExecutor.execute(validators);

		List<Message> messages = Message.findAllMessages();
		assertThat(messages).hasSize(2);
		assertThat(messages).onProperty("text").containsOnly("error 1", "error 2");
	}

    /**
     * Test that adds a new {@link ValidationListener} to the
     * validatorExecutor. The new {@link ValidationListener} must be called
     * accordingly.
     */
    @Test
    public void addNewListener() {
        List<Validator> validators = new ArrayList<Validator>();
        validators.add(new Validator() {

            @Override
            public void validate(ValidationMessageContext messageContext) {
                messageContext.error("error 1");
            }
        });

        final List<Message> messages = Lists.newArrayList();
        validatorExecutor.addValidationListener(new ValidationListener() {
            @Override
            public void messageCreated(Message message) {
                messages.add(message);
            }

            @Override
            public void preValidation() { }

            @Override
            public void preValidation(Validator validator) { }

            @Override
            public void postValidation(Validator validator, Message... messages) { }

            @Override
            public void postValidation(Message... messages) { }
        });
        validatorExecutor.execute(validators);

        assertThat(messages).hasSize(1);
        assertThat(messages).onProperty("text").containsOnly("error 1");
    }
}
