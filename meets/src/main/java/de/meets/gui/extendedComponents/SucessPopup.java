package de.meets.gui.extendedComponents;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.meets.gui.ViewName;

public class SucessPopup extends Window {
	
	public SucessPopup(String title, String state) {
		super(title);
		
		Label lInformation = new Label("Dein Meeting " + title + " wurde erfolgreich " + state);
		
		HorizontalLayout backLayout = new HorizontalLayout();
		backLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		backLayout.setSizeFull();
		Button bSave = new Button("Zur Übersicht");
		bSave.addClickListener(e -> {
			getUI().getNavigator().navigateTo(ViewName.OVERVIEW.toString());
			close();
		});
		backLayout.addComponent(bSave);

		HorizontalLayout cancelLayout = new HorizontalLayout();
		cancelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		cancelLayout.setSizeFull();
		Button bCancel = new Button("Weiter Bearbeiten");
		bCancel.addClickListener(e -> {
			close();
		});
		cancelLayout.addComponent(bCancel);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		buttonLayout.setWidth(75, Unit.PERCENTAGE);
		buttonLayout.addComponents(backLayout, cancelLayout);
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.addComponents(lInformation, buttonLayout);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		layout.setMargin(true);
		this.setContent(layout);
		
		
		center();
		this.setWidth(25, Unit.PERCENTAGE);
	}
	
}
