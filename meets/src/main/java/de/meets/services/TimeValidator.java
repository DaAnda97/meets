package de.meets.services;

import com.vaadin.data.validator.AbstractValidator;

public class TimeValidator extends AbstractValidator<String> {

	public TimeValidator(String errorMessage) {
		super(errorMessage);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isValidValue(String value) {
		// http://www.mkyong.com/regular-expressions/how-to-validate-time-in-24-hours-format-with-regular-expression/
		if (!value.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
			return false;
		}
		return true;
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
