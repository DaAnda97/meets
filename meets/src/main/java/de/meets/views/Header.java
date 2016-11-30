package de.meets.views;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;

import de.meets.vaadin_archetype_application.MeetsUI;
import de.meets.vaadin_archetype_application.Views;

public class Header extends HorizontalLayout{
	Button showUser = new Button("Mein Profil");
	Button logout = new Button("Abmelden");
	
	public Header() {
		ClassResource logo =  new ClassResource("/images/logo.png");
		this.addComponent(new Image(null,logo));
	}
	
	public void addShowUser(){
		showUser.addClickListener(e -> {
			getUI().getNavigator().navigateTo(Views.SHOW_USER.getView());
		});
		this.addComponent(showUser);
	}
	public void removeShowUser(){
		this.removeComponent(showUser);
	}
	
	public void addLogout(){
		logout.addClickListener(e -> {
			MeetsUI.logout();
		});
		this.addComponent(logout);
	}
	public void removeLogout(){
		this.removeComponent(logout);
	}

}
