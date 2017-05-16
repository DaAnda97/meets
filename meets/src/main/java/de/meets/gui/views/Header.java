package de.meets.gui.views;

import com.vaadin.server.ClassResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.Runo;

import de.meets.vaadin_archetype_application.MeetsUI;

public class Header extends HorizontalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6978898502928888668L;

	MeetsUI meetsUI;
	
	Button showUser = new Button("Mein Profil", FontAwesome.USER);
	Button logout = new Button("Abmelden", FontAwesome.SIGN_OUT);
	
	public Header(MeetsUI meetsUI) {
		this.meetsUI = meetsUI;
		
		Button btnLogo = new Button(new ClassResource("/images/logo.png"));
		btnLogo.setStyleName(Runo.BUTTON_LINK);
		btnLogo.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(Login.NAME));
		
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		this.setSizeFull();
		this.addComponent(btnLogo);
		this.setMargin(true);
		this.setSpacing(true);
		
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
