package com.bfds.validation.execution;

import com.bfds.validation.Validator;
import com.google.common.base.Preconditions;

public class ValidatorsManager {

	private final ValidatorFinder validatorFinder;

	private final ValidatorExecutor validatorExecutor;

	private ValidatorsManager(ValidatorFinder validatorFinder, ValidatorExecutor validatorExecutor) {
		Preconditions.checkNotNull(validatorFinder, "validatorFinder is null");
		Preconditions.checkNotNull(validatorExecutor, "validatorExecutor is null");

		this.validatorFinder = validatorFinder;
		this.validatorExecutor = validatorExecutor;
	}

	public void executeValidators() {
		this.validatorExecutor.execute(this.validatorFinder.find());
	}

	public static ValidatorsManager newInstance(ValidatorFinder validatorFinder, ValidatorExecutor validatorExecutor) {
		return new ValidatorsManager(validatorFinder, validatorExecutor);
	}
}
