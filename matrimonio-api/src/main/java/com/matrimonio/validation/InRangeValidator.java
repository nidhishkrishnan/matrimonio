package com.matrimonio.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.matrimonio.domain.Range;

@Component
public class InRangeValidator implements ConstraintValidator<InRange, Range> {

	private int min;
	private int max;

	@Override
	public void initialize(InRange constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(Range value, ConstraintValidatorContext context) {
		return value == null || (value.getStart() >= this.min && value.getEnd() <= this.max && value.getEnd() > this.min);
	}
}
