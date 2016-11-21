package de.meets.vaadin_archetype_application;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ShowUser extends VerticalLayout implements View{
	@Override
	public void enter(ViewChangeEvent event) {
	    Label message = new Label("Deine Angaben:");
	    
	    TextField name = new TextField("Benutzername");
	    name.setValue("Dein Benutzername");
	    name.setEnabled(false);
	    
	    TextField email = new TextField("E-Mail");
	    email.setValue("Deine Mail");
	    email.setEnabled(false);
	    
	    Button edit = new Button("Bearbeiten");
	    Button saveChanges = new Button("Speichern");
	    Button removeChanges = new Button("Abbrechen");
	    
	    edit.addClickListener(e -> {
	    	name.setEnabled(true);
	    	email.setEnabled(true);
	   		this.removeComponent(edit);
	   		this.addComponents(saveChanges, removeChanges);
	    });
	    
	    saveChanges.addClickListener(e -> {
	    	name.setEnabled(false);
	    	email.setEnabled(false);
	   		this.addComponent(edit);
	   		this.removeComponent(saveChanges);
	   		this.removeComponent(removeChanges);
	    });
	    
	    removeChanges.addClickListener(e -> {
	    	name.setEnabled(false);
	    	email.setEnabled(false);
	   		this.addComponent(edit);
	   		this.removeComponent(saveChanges);
	   		this.removeComponent(removeChanges);
	    });
	    
	    this.addComponents(message, name, email, edit);
	    this.setMargin(true);
	    this.setSpacing(true);
	}
}
