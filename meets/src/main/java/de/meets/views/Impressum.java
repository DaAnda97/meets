package de.meets.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.meets.vaadin_archetype_application.MeetsUI;

public class Impressum extends VerticalLayout implements View{
	public static final String NAME = "impressum";

	@Override
	public void enter(ViewChangeEvent event) {
		for(int i=0; i<20; i++){
			this.addComponent(new Label(""));
			this.addComponent(new Label("Impressum"));
		}
	}

}
