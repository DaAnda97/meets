package de.meets.vaadin_archetype_application;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.meets.asset_manager.MemberManager;
import de.meets.assets.Member;

public class ShowUser extends HorizontalLayout implements View{
	MemberManager memberManager = new MemberManager();
	Member member = NavigatorUI.getRegistratedMember();
	
	VerticalLayout informationPanel = new VerticalLayout();
	Label message = new Label("Deine Angaben:");
	TextField name = new TextField("Benutzername");
	TextField email = new TextField("E-Mail");
//	TextField location = new TextField("Ort");
	
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
    
    Button deliteUser = new Button("Benutzer löschen!");
    
    
	@Override
	public void enter(ViewChangeEvent event) {
		//-------------------- INFORMATION - PANEL --------------------------
	    //Ort Informationen zu angemedeten Benutzer ersetzen
	    name.setValue(member.getUsername());
	    email.setValue(member.getEmail());
//	    location.setValue("Dein Ort");
	    
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
	    
	    informationPanel.addComponents(message, name, email, buttonPanel);
	    
	    
	    //-------------------- PASSWORD - PANEL ---------------------------
	    confirmNewPassoword.addClickListener(e -> {
	    	confirmNewPassoword();
	    });
	    passwordPanel.addComponents(changePassword, passwordOld, passwordNew, passwordNewConfirm, confirmNewPassoword);
	    
	    
	    //------------------------ MAIN - PANEL ---------------------------
	    
	    deliteUser.addClickListener(e -> {
	    	memberManager.delete(member);
	    	getUI().getNavigator().navigateTo("Login");
	    });
	    
	    this.addComponents(informationPanel, passwordPanel, deliteUser);
	    this.setMargin(true);
	    this.setSpacing(true);
	}

	private void saveChanges() {
		System.out.println(member.getUsername());
		member.setUsername(name.getValue());
		member.setEmail(email.getValue());
//		String locationValue = location.getValue(); //TODO Location ändern
		memberManager.update(member);
		
		message.setValue("Änderungen gespeichert!");
	}

	private void changeToViewMode() {
		name.setEnabled(false);
    	email.setEnabled(false);
//    	location.setEnabled(false);
    	buttonPanel.addComponent(edit);
   		buttonPanel.removeComponent(saveChanges);
   		buttonPanel.removeComponent(removeChanges);
	}
	
	private void changeToEditMode() {
		name.setEnabled(true);
    	email.setEnabled(true);
//    	location.setEnabled(true);
    	buttonPanel.removeComponent(edit);
    	buttonPanel.addComponents(saveChanges, removeChanges);
	}

	private void confirmNewPassoword() {
		String oldPassword = passwordOld.getValue();
		String newPassword = passwordNew.getValue();
		String newPasswordConfirm = passwordNewConfirm.getValue();
		
		if (member.getPassword().equals(oldPassword)){
			if (newPassword.equals(newPasswordConfirm)){
				changePassword.setValue("Passwort geändert!");
				member.setPassword(newPassword);
				memberManager.update(member);
				
			} else {
				changePassword.setValue("Neue Passwörter stimmen nicht überein!");
			}
		} else {
			changePassword.setValue("Falsches Passwort!");
		}
		
		passwordOld.setValue("");
		passwordNew.setValue("");
		passwordNewConfirm.setValue("");
	}
}
