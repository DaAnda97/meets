package de.meets.services;

import com.vaadin.data.validator.AbstractValidator;

public class MaxValueValidator extends AbstractValidator<String> {

	public MaxValueValidator(String errorMessage) {
		super(errorMessage);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isValidValue(String value) {

		if (value != null && (!value.matches("\\d"))) {
			int inputDecimal = Integer.parseInt(value);
			if (inputDecimal < 1 && inputDecimal > 1e12){
				//smaller then 0 or taller then 1 billion
				return false;
			}
		}
		return true;
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
