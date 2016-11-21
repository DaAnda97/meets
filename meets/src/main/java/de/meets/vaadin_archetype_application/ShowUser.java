package de.meets.vaadin_archetype_application;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ShowUser extends HorizontalLayout implements View{
	VerticalLayout informationPanel = new VerticalLayout();
	Label message = new Label("Deine Angaben:");
	TextField name = new TextField("Benutzername");
	TextField email = new TextField("E-Mail");
	TextField location = new TextField("Ort");
	
	HorizontalLayout buttonPanel = new HorizontalLayout();
    Button edit = new Button("Bearbeiten");
    Button saveChanges = new Button("Speichern");
    Button removeChanges = new Button("Abbrechen");
	
    
    VerticalLayout passwordPanel = new VerticalLayout();
    Label changePassword = new Label("Passwort ändern:");
    PasswordField passwordOld = new PasswordField("Altes Passwort");
    PasswordField passwordNew = new PasswordField("Neues Passwort");
    PasswordField passwordNewConfirm = new PasswordField("Bestätige neues Passwort");
    Button confirmNewPassoword = new Button("Bestätigen");
    
    
	@Override
	public void enter(ViewChangeEvent event) {
		//-------------------- INFORMATION - PANEL --------------------------
	    //Angaben durch Informationen zu angemedeten Benutzer ersetzen
	    name.setValue("Dein Benutzername");
	    email.setValue("Deine Mail");
	    location.setValue("Dein Ort");
	    
	    changeToViewMode();
	    
	    // Button ClickListener
	    edit.addClickListener(e -> {
	    	changeToEditMode();
	    });
	    saveChanges.addClickListener(e -> {
	    	saveChanges();
	    	changeToViewMode();
	    });
	    removeChanges.addClickListener(e -> {
	    	changeToViewMode();
	    });
	    buttonPanel.addComponent(edit);
	    
	    informationPanel.addComponents(message, name, email, location, buttonPanel);
	    
	    
	    //-------------------- PASSWORD - PANEL ---------------------------
	    confirmNewPassoword.addClickListener(e -> {
	    	confirmNewPassoword();
	    });
	    passwordPanel.addComponents(changePassword, passwordOld, passwordNew, passwordNewConfirm, confirmNewPassoword);
	    
	    
	    //------------------------ MAIN - PANEL ---------------------------
	    
	    this.addComponents(informationPanel, passwordPanel);
	    this.setMargin(true);
	    this.setSpacing(true);
	}

	private void saveChanges() {
		String nameValue = name.getValue();
		String emailValue = email.getValue();
		String locationValue = location.getValue();
		
		//TODO Methodenaufruf an SQLAgent, der Änderungen speichert
	}

	private void changeToViewMode() {
		name.setEnabled(false);
    	email.setEnabled(false);
    	location.setEnabled(false);
    	buttonPanel.addComponent(edit);
   		buttonPanel.removeComponent(saveChanges);
   		buttonPanel.removeComponent(removeChanges);
	}
	
	private void changeToEditMode() {
		name.setEnabled(true);
    	email.setEnabled(true);
    	location.setEnabled(true);
    	buttonPanel.removeComponent(edit);
    	buttonPanel.addComponents(saveChanges, removeChanges);
	}

	private void confirmNewPassoword() {
		String oldPassword = name.getValue();
		String newPassword = email.getValue();
		String newPasswordConfirm = location.getValue();
		
		if (newPassword.equals(newPasswordConfirm)){
			//TODO Stimmt altesPasswort -> speichern
			
		} else {
			//TODO Fehlermeldung
		}
		
		passwordOld.setValue("");
		passwordNew.setValue("");
		passwordNewConfirm.setValue("");
	}
}
