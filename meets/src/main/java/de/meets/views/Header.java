package de.meets.views;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;

import de.meets.vaadin_archetype_application.Views;

public class Header extends HorizontalLayout{
	
	public Header() {
		ClassResource logo =  new ClassResource("/images/logo.png");
		this.addComponent(new Image(null,logo));
	}
	
	public void addShowUser(){
		Button showUserButton = new Button("Mein Profil");
		showUserButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(Views.SHOW_USER.getView());
		});
		this.addComponent(showUserButton);
	}
	
	

}
