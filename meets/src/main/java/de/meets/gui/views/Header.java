package de.meets.gui.views;

import com.vaadin.server.ClassResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

import de.meets.gui.ViewName;
import de.meets.vaadin_archetype_application.MeetsUI;

public class Header extends VerticalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6978898502928888668L;

	MeetsUI meetsUI;
	
	HorizontalLayout headerMenu;
	Button showUser;
	Button logout;
	
	public Header(MeetsUI meetsUI) {
		this.meetsUI = meetsUI;
		
		HorizontalLayout logoLayout = new HorizontalLayout();
		logoLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		logoLayout.setSizeFull();
		Button bLogo = new Button(new ClassResource("/images/logo.png"));
		bLogo.setStyleName(Runo.BUTTON_LINK);
		bLogo.addClickListener(clickEvent -> {
			if ( meetsUI.getRegistratedMember() == null ) {
				getUI().getNavigator().navigateTo(ViewName.LOGIN.toString());
			} else {
				getUI().getNavigator().navigateTo(ViewName.OVERVIEW.toString());
			}
		});
		logoLayout.addComponent(bLogo);

		this.showUser = new Button("Mein Profil", FontAwesome.USER);
		showUser.addClickListener(e -> {
			meetsUI.showUser();
		});
		this.logout = new Button("Abmelden", FontAwesome.SIGN_OUT);
		logout.addClickListener(e -> {
			meetsUI.logout();
		});
		
		this.headerMenu = new HorizontalLayout();
		headerMenu.setMargin(true);
		headerMenu.setSpacing(true);

		HorizontalLayout rightAlignmentHeaderMenu = new HorizontalLayout();
		rightAlignmentHeaderMenu.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		rightAlignmentHeaderMenu.setSizeFull();
		rightAlignmentHeaderMenu.addComponent(headerMenu);
		
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		this.setSizeFull();
		this.addComponents(rightAlignmentHeaderMenu, logoLayout);
		this.setMargin(true);
		this.setSpacing(true);
		

	}
	
	public void addShowUser(){
		this.headerMenu.addComponent(showUser);
	}
	public void removeShowUser(){
		this.headerMenu.removeComponent(showUser);
	}
	
	public void addLogout(){
		this.headerMenu.addComponent(logout);
	}
	public void removeLogout(){
		this.headerMenu.removeComponent(logout);
	}

}
