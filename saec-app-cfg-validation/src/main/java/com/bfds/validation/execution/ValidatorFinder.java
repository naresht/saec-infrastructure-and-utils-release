package com.bfds.validation.execution;

import java.util.Collection;

import com.bfds.validation.Validator;

public interface ValidatorFinder {

	Collection<Validator> find();
}
