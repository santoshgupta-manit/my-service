package com.abn.assessment.recipe.validator;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
@Target({FIELD,PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = OneOfValidator.class)
public @interface OneOf {
	
	  String message() default "Not a valid value";

	     Class<?>[] groups() default { };

	     Class<? extends Payload>[] payload() default { };

	     /** The array of allowed values. */
	     String[] value();

}
