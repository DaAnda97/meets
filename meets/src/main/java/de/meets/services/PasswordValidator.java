package de.meets.services;

import com.vaadin.data.validator.AbstractValidator;

/*
 * Akzeptiert momentan alle Eingaben
 */
public class PasswordValidator extends AbstractValidator<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 215256696598444695L;

	public PasswordValidator(String errorMsg) {
		super(errorMsg);
	}

	
	
	@Override
	protected boolean isValidValue(String value) {
		//
		// Password must be at least 8 characters long and contain at least
		// one number
		//
		// if (value != null && (value.length() < 8 ||
		// !value.matches(".*\\d.*"))) {
		// return false;
		// }
		return true;
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}
}
