package de.meets.views;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;

public class Header extends HorizontalLayout{
	
	public Header() {
		ClassResource logo =  new ClassResource("/images/logo.png");
		this.addComponent(new Image(null,logo));
//		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//		this.setSizeFull();
//        this.setMargin(true);
//        this.setSpacing(true);
	}
	
	

}
