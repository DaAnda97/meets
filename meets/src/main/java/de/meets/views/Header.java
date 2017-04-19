package de.meets.views;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;

import de.meets.vaadin_archetype_application.MeetsUI;

public class Header extends HorizontalLayout{
	MeetsUI meetsUI;
	
	Button showUser = new Button("Mein Profil");
	Button logout = new Button("Abmelden");
	
	public Header(MeetsUI meetsUI) {
		this.meetsUI = meetsUI;
		
		ClassResource logo =  new ClassResource("/images/logo.png");
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		this.setSizeFull();
		this.addComponent(new Image(null,logo));
		
		showUser.addClickListener(e -> {
			meetsUI.showUser();
		});
		
		logout.addClickListener(e -> {
			meetsUI.logout();
		});
	}
	
	public void addShowUser(){
		this.addComponent(showUser);
	}
	public void removeShowUser(){
		this.removeComponent(showUser);
	}
	
	public void addLogout(){
		this.addComponent(logout);
	}
	public void removeLogout(){
		this.removeComponent(logout);
	}

}
