package de.meets.gui.extendedComponents;

import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class ExtendedTextField extends HorizontalLayout {
	Label caption;
	AbstractTextField textField;
	Label message;
	
	public ExtendedTextField(String caption, AbstractTextField textField, String errorMessage) {
		this.caption = new Label(caption);
		this.message = new Label(errorMessage);
		this.textField = textField;
		
		this.textField.setWidth(10, Unit.EM);
		
		this.message.setVisible(false);
		
		this.setSizeFull();
		this.addComponents(this.caption, this.textField, this.message);
	}
	
	@Override
	public void setCaption(String caption) {
		this.caption.setCaption(caption);
	}
	
	@Override
	public void setStyleName(String style) {
		caption.setStyleName(style);
		message.setStyleName(style);
		super.setStyleName(style);
	}
	
	public void setRequired(boolean isRequired){
		textField.setRequired(isRequired);
	}
	
	public void setInputPrompt(String inputPrompt){
		textField.setInputPrompt(inputPrompt);
	}
	
	public void addValidator(Validator validator){
		textField.addValidator(validator);
	}
	
	public void setInvalidAllowed(boolean invalidAllowed){
		textField.setInvalidAllowed(invalidAllowed);
	}
	
	public boolean isValid(){
		return textField.isValid();
	}
	
	public String getValue(){
		return textField.getValue();
	}
	
	public void setValue(String value){
		textField.setValue(value);
	}
	
	public void focus(){
		textField.focus();
	}
	
	public void showErrorMessage(){
		this.message.setVisible(true);
	}
	
}
