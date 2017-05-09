package de.meets.gui.extendedComponents;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public class ExtendedTextField extends CustomComponent {
	private Label caption;
	private AbstractTextField textField;
	private Label message;
	private String errorMessage;
	
	public ExtendedTextField(String caption, AbstractTextField textField, String errorMessage) {
		Panel panel = new Panel();
		HorizontalLayout panelContent = new HorizontalLayout();
		panel.setContent(panelContent);
		
		this.caption = new Label(caption);
		this.caption.setWidth(8, Unit.EM);
		
		this.textField = textField;
		this.textField.setWidth(15, Unit.EM);
		
		this.message = new Label("");
		this.message.setWidth(20, Unit.EM);
		this.errorMessage = errorMessage;
		
		panelContent.addComponents(this.caption, this.textField, this.message);
		panelContent.setSpacing(true);
		setCompositionRoot(panel);
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
	
	public void addValidator(Validator validator, String errorMessage){
		textField.addBlurListener(new BlurListener() {

			public void blur(BlurEvent event) {
				try {
					validator.validate(textField.getValue());
					message.setValue("");
				} catch (InvalidValueException exception) {
					message.setValue(errorMessage);
				}
			}
		});
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
		this.message.setValue(errorMessage);
	}

	
}
