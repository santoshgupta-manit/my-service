package com.abn.assessment.recipe.validator;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OneOfValidator implements ConstraintValidator<OneOf, String> {

	private Set<String> allowedValues;

	@Override
	public void initialize(OneOf constraintAnnotation) {
		allowedValues = Set.of(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
		return str == null || allowedValues.contains(str.toLowerCase());
	}
}
